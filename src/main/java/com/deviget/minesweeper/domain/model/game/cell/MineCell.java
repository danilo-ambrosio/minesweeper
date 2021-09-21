package com.deviget.minesweeper.domain.model.game.cell;

public class MineCell extends Cell {

  public static final MineCell at(final int index) {
    return new MineCell(index);
  }

  private MineCell(final int index) {
    this(index, CellStatus.COVERED);
  }

  private MineCell(final int index, final CellStatus status) {
    super(index, CellType.MINE, status);
  }

  @Override
  public Cell incrementMine() {
    return this;
  }

  @Override
  public Cell uncover(final UncoveringType uncoveringType) {
    if(uncoveringType.isPropagation()) {
      return this;
    }
    return new MineCell(index(), CellStatus.UNCOVERED);
  }

  @Override
  public boolean shouldPropagateUncovering() {
    return false;
  }

  @Override
  public boolean isMine() {
    return true;
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
