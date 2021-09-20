package com.deviget.minesweeper.domain.model.game;

public class MineCell extends Cell {

  public static final MineCell at(final int index) {
    return new MineCell(index);
  }

  private MineCell(final int index) {
    super(index, CellType.MINE);
  }

}
