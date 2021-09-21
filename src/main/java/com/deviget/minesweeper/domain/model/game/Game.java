package com.deviget.minesweeper.domain.model.game;

import com.deviget.minesweeper.domain.model.game.cell.Cell;
import com.deviget.minesweeper.domain.model.game.cell.CellCoordinate;
import com.deviget.minesweeper.domain.model.game.cell.UncoveringType;
import com.deviget.minesweeper.domain.model.user.UserId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "game")
public class Game {

  @Id
  private final GameId id;
  private final UserId userId;
  private final Preferences preferences;
  private final Board board;
  private GameStatus status;
  private final long startedOn;
  private long updatedOn;

  public static Game configure(final Preferences preferences,
                               final UserId userId) {
    final long now = Game.currentTimeMillis();
    return new Game(GameId.create(), GameStatus.NEW, preferences,
            Board.empty(preferences), now, now, userId);
  }

  @PersistenceConstructor
  private Game(final GameId id,
              final GameStatus status,
              final Preferences preferences,
              final Board board,
              final long startedOn,
              final long updatedOn,
              final UserId userId) {
    this.id = id;
    this.status = status;
    this.preferences = preferences;
    this.board = board;
    this.startedOn = startedOn;
    this.updatedOn = updatedOn;
    this.userId = userId;
  }

  public void uncoverCell(final CellCoordinate cellCoordinate) {
    if(isNew()) {
      this.status = GameStatus.ONGOING;
      this.board.placeMines(resolveMineCoordinates(cellCoordinate));
    }

    final Cell uncoveredCell =
            this.board.uncoverCell(cellCoordinate, UncoveringType.USER_REQUEST);

    if(!this.board.hasCellsToBeUncovered()) {
      this.status = GameStatus.WON;
    }

    if(uncoveredCell.isMine()) {
      this.status = GameStatus.LOST;
    }

    this.updateTimestamp();
  }

  public void placeQuestionMark(final CellCoordinate cellCoordinate) {
    this.board.placeQuestionMark(cellCoordinate);
    this.updateTimestamp();
  }

  public void placeFlag(final CellCoordinate cellCoordinate) {
    this.board.placeFlag(cellCoordinate);
    this.updateTimestamp();
  }

  public void resume() {
    updateTimestamp();
  }

  private Set<CellCoordinate> resolveMineCoordinates(final CellCoordinate ignoredCoordinate) {
    final Set<CellCoordinate> mineCoordinates = new HashSet<>();

    while(mineCoordinates.size() < preferences.numberOfMines()) {
      final CellCoordinate randomCoordinate =
              CellCoordinate.random(preferences.boardSize());

      if(!ignoredCoordinate.equals(randomCoordinate)) {
        mineCoordinates.add(randomCoordinate);
      }
    }

    return mineCoordinates;
  }

  private void updateTimestamp() {
    this.updatedOn = currentTimeMillis();
  }

  public GameId id() {
    return id;
  }

  public UserId userId() {
    return userId;
  }

  public List<Row> rows() {
    return board.rows();
  }

  public GameStatus status() {
    return status;
  }

  public long startedOn() {
    return startedOn;
  }

  public long updatedOn() {
    return updatedOn;
  }

  private boolean isNew() {
    return this.status.equals(GameStatus.NEW);
  }

  private static long currentTimeMillis() {
    return Instant.now().toEpochMilli();
  }

}
