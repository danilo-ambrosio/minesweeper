package com.deviget.minesweeper.infra.service;

public class GameStatusTransition {

  public final Reason reason;
  public final long timeElapsed;

  public static GameStatusTransition becauseOf(final String reason,
                                               final long timeElapsed) {
    return new GameStatusTransition(Reason.valueOf(reason), timeElapsed);
  }

  public GameStatusTransition(final Reason reason, final long timeElapsed) {
    this.reason = reason;
    this.timeElapsed = timeElapsed;
  }

  public enum Reason {
    GAME_PRESERVATION,
    GAME_CONTINUATION
  }

}
