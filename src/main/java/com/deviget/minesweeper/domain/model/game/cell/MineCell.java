package com.deviget.minesweeper.domain.model.game.cell;

public class MineCell extends Cell {

  public static final MineCell at(final int index) {
    return new MineCell(index);
  }

  private MineCell(final int index) {
    super(index, CellType.MINE);
  }

  @Override
  public Cell incrementMine() {
    return this;
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
