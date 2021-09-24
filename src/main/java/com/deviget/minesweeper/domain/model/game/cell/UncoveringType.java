package com.deviget.minesweeper.domain.model.game.cell;

/**
 * {@code UncoveringType} classifies the origin of
 * an uncovering request.
 *
 * @author Danilo Ambrosio
 */
public enum UncoveringType {
  USER_REQUEST,
  GAME_ENDING,
  PROPAGATION;

  public boolean isPropagation() {
    return equals(PROPAGATION);
  }

  public boolean isGameEnding() {
    return equals(GAME_ENDING);
  }
}
