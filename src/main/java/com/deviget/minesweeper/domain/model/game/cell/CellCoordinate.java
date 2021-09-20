package com.deviget.minesweeper.domain.model.game.cell;

import com.deviget.minesweeper.domain.model.game.BoardSize;

import java.util.Objects;
import java.util.Random;

public class CellCoordinate {

   final int rowIndex;
   final int cellIndex;

  public static CellCoordinate random(final int rows, final int cells) {
    return with(new Random().nextInt(rows), new Random().nextInt(cells));
  }

  public static CellCoordinate invalid() {
    return with(-1, -1);
  }

  public static CellCoordinate with(final int rowIndex, final int cellIndex) {
    return new CellCoordinate(rowIndex, cellIndex);
  }

  private CellCoordinate(int rowIndex, int cellIndex) {
    this.rowIndex = rowIndex;
    this.cellIndex = cellIndex;
  }

  public int rowIndex() {
    return rowIndex;
  }

  public int cellIndex() {
    return cellIndex;
  }

  public boolean isTop() {
    return rowIndex == 0;
  }

  public boolean isLeftEnd() {
    return cellIndex == 0;
  }

  public boolean isBottom(final BoardSize boardSize) {
    return rowIndex + 1 == boardSize.rows();
  }

  public boolean isValid() {
    return rowIndex >= 0 && cellIndex >= 0;
  }

  public boolean isRightEnd(final BoardSize boardSize) {
    return cellIndex + 1 == boardSize.columns();
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
