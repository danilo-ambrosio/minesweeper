package com.deviget.minesweeper.domain.model.game;

/**
 * {@code BoardSize} carries the information on how many rows
 * and columns constitute a {@link Board} in a given {@link Game}
 *
 * @author Danilo Ambrosio
 */
public class BoardSize {

  private final int rows;
  private final int columns;

  public BoardSize(final int rows, final int columns) {
    this.rows = rows;
    this.columns = columns;
  }

  public int rows() {
    return rows;
  }

  public int columns() {
    return columns;
  }
}
