package com.deviget.minesweeper.domain.model.game;

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
