package com.deviget.minesweeper.domain.model;

public abstract class Cell {

  private final int index;
  private final CellType cellType;
  private final CellStatus cellStatus;

  protected Cell(final int index, final CellType cellType) {
    this.index = index;
    this.cellType = cellType;
    this.cellStatus = CellStatus.UNCOVERED;
  }

}
