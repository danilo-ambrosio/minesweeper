package com.deviget.minesweeper.domain.model.game.cell;

public class EmptyCell extends Cell {

  public static Cell at(final int index) {
    return new EmptyCell(index);
  }

  protected EmptyCell(final int index) {
    super(index, CellType.EMPTY);
  }

  @Override
  public Cell incrementMine() {
    return Cell.mineAlertAt(this.index);
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
