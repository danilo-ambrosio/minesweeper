package br.com.plena.minesweeper.infra.resources.data;

public class GameStatusTransitionData {

  public final String reason;

  public static GameStatusTransitionData from(final String reason) {
    return new GameStatusTransitionData(reason);
  }

  public GameStatusTransitionData() {
    this("");
  }

  private GameStatusTransitionData(final String reason) {
    this.reason = reason;
  }
}
