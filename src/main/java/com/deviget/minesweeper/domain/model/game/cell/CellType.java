package com.deviget.minesweeper.domain.model.game.cell;

/**
 * {@code CellType} discriminates the {@code Cell} content
 * and behavior.
 *
 * @author Danilo Ambrosio
 */
public enum CellType {
  EMPTY,
  MINE,
  MINE_ALERT
}
