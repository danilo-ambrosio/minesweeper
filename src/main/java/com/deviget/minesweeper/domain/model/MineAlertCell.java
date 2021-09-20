package com.deviget.minesweeper.domain.model;

public class MineAlertCell extends Cell {

  protected MineAlertCell(final int index) {
    super(index, CellType.MINE_ALERT);
  }

}
