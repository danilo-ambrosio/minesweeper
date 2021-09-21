package com.deviget.minesweeper.domain.model.game.cell;

public class EmptyCell extends Cell {

  public static Cell at(final int index) {
    return new EmptyCell(index);
  }

  protected EmptyCell(final int index) {
    this(index, CellStatus.COVERED);
  }

  protected EmptyCell(final int index, final CellStatus cellStatus) {
    super(index, CellType.EMPTY, cellStatus);
  }

  @Override
  public Cell uncover(final UncoveringType uncoveringType) {
    return new EmptyCell(index(), CellStatus.UNCOVERED);
  }

  @Override
  public boolean shouldPropagateUncovering() {
    return true;
  }

  @Override
  public Cell incrementMine() {
    return Cell.mineAlertAt(index());
  }

  @Override
  public boolean equals(final Object other) {
    return super.equals(other);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
