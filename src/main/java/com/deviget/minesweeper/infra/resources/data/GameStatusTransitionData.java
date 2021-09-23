package com.deviget.minesweeper.infra.resources.data;

public class GameStatusTransitionData {

  public final String reason;
  public final long timeElapsed;

  public GameStatusTransitionData(final String reason, final long timeElapsed) {
    this.reason = reason;
    this.timeElapsed = timeElapsed;
  }
}
