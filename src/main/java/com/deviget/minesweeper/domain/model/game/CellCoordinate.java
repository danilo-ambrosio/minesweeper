package com.deviget.minesweeper.domain.model.game;

import java.util.Objects;

public class CellCoordinate {

  final int rowIndex;
  final int cellIndex;

  public static CellCoordinate with(final int rowIndex, final int cellIndex) {
    return new CellCoordinate(rowIndex, cellIndex);
  }

  private CellCoordinate(int rowIndex, int cellIndex) {
    this.rowIndex = rowIndex;
    this.cellIndex = cellIndex;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CellCoordinate that = (CellCoordinate) o;
    return rowIndex == that.rowIndex && cellIndex == that.cellIndex;
  }

  @Override
  public int hashCode() {
    return Objects.hash(rowIndex, cellIndex);
  }

}
