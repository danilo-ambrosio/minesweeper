package com.deviget.minesweeper.domain.model.game;

import com.deviget.minesweeper.domain.model.game.cell.Cell;
import com.deviget.minesweeper.domain.model.game.cell.CellCoordinate;
import com.deviget.minesweeper.domain.model.game.cell.UncoveringType;
import com.deviget.minesweeper.domain.model.user.UserId;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {

  private final GameId gameId;
  private final UserId userId;
  private final Preferences preferences;
  private final Board board;
  private GameStatus status;
  private final long startedOn;
  private long updatedOn;

  public static Game configure(final Preferences preferences,
                               final UserId userId) {
    return new Game(preferences, userId);
  }

  private Game(final Preferences preferences,
               final UserId userId) {
    this.gameId = GameId.create();
    this.status = GameStatus.NEW;
    this.preferences = preferences;
    this.board = Board.empty(preferences);
    this.startedOn = currentTimeMillis();
    this.updatedOn = startedOn;
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
    return gameId;
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

  private long currentTimeMillis() {
    return Instant.now().toEpochMilli();
  }

}
