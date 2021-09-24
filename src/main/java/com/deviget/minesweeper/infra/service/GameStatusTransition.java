package com.deviget.minesweeper.infra.service;

public class GameStatusTransition {

  public final Reason reason;

  public static GameStatusTransition becauseOf(final String reason) {
    return new GameStatusTransition(Reason.valueOf(reason));
  }

  public GameStatusTransition(final Reason reason) {
    this.reason = reason;
  }

  public enum Reason {
    GAME_PRESERVATION,
    GAME_CONTINUATION
  }

}
