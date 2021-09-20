package com.deviget.minesweeper.domain.model;

public class MineCell extends Cell {

  protected MineCell(int index) {
    super(index, CellType.MINE);
  }

}
