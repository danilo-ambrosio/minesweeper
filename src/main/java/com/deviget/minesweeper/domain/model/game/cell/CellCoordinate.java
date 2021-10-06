package com.deviget.minesweeper.domain.model.game.cell;

import com.deviget.minesweeper.domain.model.game.BoardSize;

import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * {@code CellCoordinate} is the combination of the row index and
 * the cell index that gives the exact logical reference to {@link Cell}.
 *
 * @author Danilo Ambrosio
 */
public class CellCoordinate {

  public final int rowIndex;
  public final int cellIndex;

  public static CellCoordinate random(final BoardSize boardSize) {
    return with(new Random().nextInt(boardSize.rows()), new Random().nextInt(boardSize.columns()));
  }

  public static CellCoordinate invalid() {
    return with(-1, -1);
  }

  public static CellCoordinate with(final int rowIndex, final int cellIndex) {
    return new CellCoordinate(rowIndex, cellIndex);
  }

  private CellCoordinate(final int rowIndex, final int cellIndex) {
    this.rowIndex = rowIndex;
    this.cellIndex = cellIndex;
  }

  public Set<CellCoordinate> resolveAdjacent(final BoardSize boardSize) {
    return Stream.of(upLeft(), up(), upRight(boardSize), right(boardSize),
                    lowRight(boardSize), low(boardSize), lowLeft(boardSize),
                    left()).filter(CellCoordinate::isValid).collect(toSet());
  }

  public int rowIndex() {
    return rowIndex;
  }

  public int cellIndex() {
    return cellIndex;
  }

  private boolean isTop() {
    return rowIndex == 0;
  }

  private boolean isLeftEnd() {
    return cellIndex == 0;
  }

  private boolean isBottom(final BoardSize boardSize) {
    return rowIndex + 1 == boardSize.rows();
  }

  private boolean isValid() {
    return rowIndex >= 0 && cellIndex >= 0;
  }

  private boolean isRightEnd(final BoardSize boardSize) {
    return cellIndex + 1 == boardSize.columns();
  }

  private CellCoordinate upLeft() {
    return isTop() || isLeftEnd() ?
            CellCoordinate.invalid() : CellCoordinate.with(rowIndex - 1, cellIndex - 1);
  }

  private CellCoordinate up() {
    return isTop() ? CellCoordinate.invalid() : CellCoordinate.with(rowIndex - 1, cellIndex);
  }

  private CellCoordinate upRight(final BoardSize boardSize) {
    return isTop() || isRightEnd(boardSize) ?
            CellCoordinate.invalid() : CellCoordinate.with(rowIndex - 1, cellIndex + 1);
  }

  private CellCoordinate right(final BoardSize boardSize) {
    return isRightEnd(boardSize) ?
            CellCoordinate.invalid() : CellCoordinate.with(rowIndex, cellIndex + 1);
  }

  private CellCoordinate lowRight(final BoardSize boardSize) {
    return isBottom(boardSize) || isRightEnd(boardSize) ?
            CellCoordinate.invalid() : CellCoordinate.with(rowIndex + 1, cellIndex + 1);
  }

  private CellCoordinate low(final BoardSize boardSize) {
    return isBottom(boardSize) ?
            CellCoordinate.invalid() : CellCoordinate.with(rowIndex + 1, cellIndex);
  }

  private CellCoordinate lowLeft(final BoardSize boardSize) {
    return isLeftEnd() || isBottom(boardSize) ?
            CellCoordinate.invalid() : CellCoordinate.with(rowIndex + 1, cellIndex - 1);
  }

  private CellCoordinate left() {
    return this.isLeftEnd() ? CellCoordinate.invalid() :  CellCoordinate.with(rowIndex, cellIndex - 1);
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
