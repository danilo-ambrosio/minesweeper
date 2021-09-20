package com.deviget.minesweeper.domain.model.game.cell;

public class MineAlertCell extends Cell {

  private final int mines;

  public static MineAlertCell at(final int index) {
    return at(index, 1);
  }

  private static MineAlertCell at(final int index, final int mines) {
    return new MineAlertCell(index, mines);
  }

  private MineAlertCell(final int index, final int mines) {
    super(index, CellType.MINE_ALERT);
    this.mines = mines;
  }

  public int mines() {
    return mines;
  }

  @Override
  public Cell incrementMine() {
    return at(index, mines + 1);
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
