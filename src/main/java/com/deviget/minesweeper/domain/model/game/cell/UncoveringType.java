package com.deviget.minesweeper.domain.model.game.cell;

public enum UncoveringType {
  USER_REQUEST,
  PROPAGATION;

  public boolean isPropagation() {
    return equals(PROPAGATION);
  }
}
