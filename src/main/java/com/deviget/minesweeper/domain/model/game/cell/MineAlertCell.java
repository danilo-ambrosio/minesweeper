package com.deviget.minesweeper.domain.model.game.cell;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("MineAlertCell")
public class MineAlertCell extends Cell {

  private final int mines;

  public static MineAlertCell at(final int index, final CellStatus status) {
    return at(index, 1, status);
  }

  public static MineAlertCell at(final int index, final int mines, final CellStatus status) {
    return new MineAlertCell(index, mines, status);
  }

  public MineAlertCell() {
    this(0, 0, CellStatus.COVERED);
  }

  @PersistenceConstructor
  protected MineAlertCell(final int index, final int mines, final CellStatus status) {
    super(index, CellType.MINE_ALERT, status);
    this.mines = mines;
  }

  @Override
  public Cell uncover(final UncoveringType uncoveringType) {
    return new MineAlertCell(index(), mines, CellStatus.UNCOVERED);
  }

  @Override
  public Cell placeQuestionMark() {
    return new MineAlertCell(index(), mines, CellStatus.QUESTION_MARKED);
  }

  @Override
  public Cell placeFlag() {
    return new MineAlertCell(index(), mines, CellStatus.FLAGGED);
  }

  @Override
  public Cell incrementMine() {
    return at(index(), mines + 1, status());
  }

  @Override
  public boolean shouldPropagateUncovering() {
    return false;
  }

  @Override
  public int mines() {
    return mines;
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
