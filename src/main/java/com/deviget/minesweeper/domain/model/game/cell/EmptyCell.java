package com.deviget.minesweeper.domain.model.game.cell;

public class EmptyCell extends Cell {

  public static Cell at(final int index) {
    return new EmptyCell(index);
  }

  protected EmptyCell(final int index) {
    this(index, CellStatus.COVERED);
  }

  protected EmptyCell(final int index, final CellStatus cellStatus) {
    super(index, CellType.EMPTY, cellStatus);
  }

  @Override
  public Cell uncover(final UncoveringType uncoveringType) {
    return new EmptyCell(index(), CellStatus.UNCOVERED);
  }

  @Override
  public Cell incrementMine() {
    return Cell.mineAlertAt(index(), status());
  }

  @Override
  public Cell placeQuestionMark() {
    return new EmptyCell(index(), CellStatus.QUESTION_MARKED);
  }

  @Override
  public Cell placeFlag() {
    return new EmptyCell(index(), CellStatus.FLAGGED);
  }

  @Override
  public boolean isEmpty() {
    return true;
  }

  @Override
  public boolean shouldPropagateUncovering() {
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
