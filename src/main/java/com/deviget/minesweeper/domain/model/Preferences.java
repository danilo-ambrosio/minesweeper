package com.deviget.minesweeper.domain.model;

public class Preferences {

  private final int rows;
  private final int columns;
  private final int mines;

  private Preferences(final int rows,
                      final int columns,
                      final int mines) {
    this.rows = rows;
    this.columns = columns;
    this.mines = mines;
  }

  public int rows() {
    return rows;
  }

  public int columns() {
    return columns;
  }

  public int mines() {
    return mines;
  }

}
