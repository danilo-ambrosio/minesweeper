package com.deviget.minesweeper.domain.model.game;

public class MineAlertCell extends Cell {

  protected MineAlertCell(final int index) {
    super(index, CellType.MINE_ALERT);
  }

}
