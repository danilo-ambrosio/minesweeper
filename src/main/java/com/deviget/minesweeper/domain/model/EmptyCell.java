package com.deviget.minesweeper.domain.model;

public class EmptyCell extends Cell {

  protected EmptyCell(final int index) {
    super(index, CellType.EMPTY);
  }

}
