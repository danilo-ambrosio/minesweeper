package com.deviget.minesweeper.domain.model.game.cell;

public enum UncoveringType {
  USER_REQUEST,
  GAME_ENDING,
  PROPAGATION;

  public boolean isPropagation() {
    return equals(PROPAGATION);
  }

  public boolean isUserRequest() {
    return equals(USER_REQUEST);
  }

  public boolean isGameEnding() {
    return equals(GAME_ENDING);
  }
}
