package com.deviget.minesweeper.domain.model.game;

import java.util.Objects;

public abstract class Cell {

  private final int index;
  private final CellType type;
  private final CellStatus status;

  public final Cell mineAt(final int index) {
    return MineCell.at(index);
  }

  public static Cell emptyAt(final int index) {
    return EmptyCell.at(index);
  }

  protected Cell(final int index, final CellType type) {
    this.index = index;
    this.type = type;
    this.status = CellStatus.UNCOVERED;
  }

  public CellType type() {
    return type;
  }

  public CellStatus status() {
    return status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cell cell = (Cell) o;
    return index == cell.index;
  }

  @Override
  public int hashCode() {
    return Objects.hash(index);
  }
}
