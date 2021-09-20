package com.deviget.minesweeper.domain.model.game;

public class EmptyCell extends Cell {

  public static Cell at(final int index) {
    return new EmptyCell(index);
  }

  protected EmptyCell(final int index) {
    super(index, CellType.EMPTY);
  }

}
