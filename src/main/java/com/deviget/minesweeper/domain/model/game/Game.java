package com.deviget.minesweeper.domain.model.game;

import com.deviget.minesweeper.domain.model.game.cell.Cell;
import com.deviget.minesweeper.domain.model.game.cell.CellCoordinate;
import com.deviget.minesweeper.domain.model.game.cell.UncoveringType;
import com.deviget.minesweeper.domain.model.user.UserId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@code Game} is the aggregate root that exposes the domain
 * behavior of a Minesweeper game to the outer application layers.
 * Its main responsibility is to manage the {@link GameStatus} flow
 * as the {@link Cell} is changed.
 *
 * @author Danilo Ambrosio
 */
@Document(collection = "game")
public class Game {

  @Id
  private final GameId id;
  private final UserId userId;
  private final Preferences preferences;
  private final Board board;
  private GameStatus status;
  private final long startedOn;
  private long lastResumedOn;
  private long timeElapsed;

  public static Game configure(final Preferences preferences,
                               final UserId userId) {
    final long now = Game.currentTimeMillis();
    return new Game(GameId.create(), userId, GameStatus.NEW, preferences, Board.empty(preferences), now, now, 0);
  }

  @PersistenceConstructor
  private Game(final GameId id,
               final UserId userId,
               final GameStatus status,
               final Preferences preferences,
               final Board board,
               final long startedOn,
               final long lastResumedOn,
               final long timeElapsed) {
    this.id = id;
    this.userId = userId;
    this.status = status;
    this.preferences = preferences;
    this.board = board;
    this.startedOn = startedOn;
    this.lastResumedOn = lastResumedOn;
    this.timeElapsed = timeElapsed;
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

    if(isOver()) {
      this.board.handleGameEnding();
    }
  }

  public void placeQuestionMark(final CellCoordinate cellCoordinate) {
    this.board.placeQuestionMark(cellCoordinate);
  }

  public void placeFlag(final CellCoordinate cellCoordinate) {
    this.board.placeFlag(cellCoordinate);
  }

  public void clearCell(final CellCoordinate cellCoordinate) {
    this.board.clear(cellCoordinate);
  }

  public void pause() {
    if(!isNew() && !isOngoing()) {
      throw new IllegalStateException("Unable to pause because game is " + this.status);
    }
    this.status = GameStatus.PAUSED;
    elapseTime();
  }

  public void resume() {
    if(!isPaused()) {
      throw new IllegalStateException("Unable to resume because game is not paused");
    }
    this.status = GameStatus.ONGOING;
    this.lastResumedOn = currentTimeMillis();
  }

  private Set<CellCoordinate> resolveMineCoordinates(final CellCoordinate protectedCoordinate) {
    final Set<CellCoordinate> mineCoordinates = new HashSet<>();

    while(mineCoordinates.size() < preferences.numberOfMines()) {
      final CellCoordinate randomCoordinate =
              CellCoordinate.random(preferences.boardSize());

      if(!protectedCoordinate.equals(randomCoordinate)) {
        mineCoordinates.add(randomCoordinate);
      }
    }

    return mineCoordinates;
  }

  private void elapseTime() {
    this.timeElapsed += Instant.ofEpochMilli(lastResumedOn).until(Instant.now(), ChronoUnit.SECONDS);
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

  public long timeElapsed() {
    return timeElapsed;
  }

  private boolean isNew() {
    return this.status.equals(GameStatus.NEW);
  }

  private boolean isPaused() {
    return this.status.equals(GameStatus.PAUSED);
  }

  private boolean isOngoing() {
    return this.status.equals(GameStatus.ONGOING);
  }

  private boolean isOver() {
    return this.status.equals(GameStatus.WON) || this.status.equals(GameStatus.LOST);
  }

  private static long currentTimeMillis() {
    return Instant.now().toEpochMilli();
  }
}
