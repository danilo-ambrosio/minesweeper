package com.deviget.minesweeper.domain.model.game;

import com.deviget.minesweeper.domain.model.UserId;

import java.time.Instant;
import java.util.List;

public class Game {

  private final GameId gameId;
  private final Board board;
  private final GameStatus status;
  private final UserId userId;
  private final Preferences preferences;
  private long startedOn;
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
    this.userId = userId;
    this.startedOn = Instant.now().toEpochMilli();
    this.updatedOn = startedOn;
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

}
