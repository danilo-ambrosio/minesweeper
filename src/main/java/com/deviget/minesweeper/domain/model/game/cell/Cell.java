package com.deviget.minesweeper.domain.model.game.cell;

import java.util.Objects;

public abstract class Cell implements Comparable<Cell> {

  private final int index;
  private final CellType type;
  private final CellStatus status;

  public static Cell mineAt(final int index) {
    return MineCell.at(index);
  }

  public static Cell mineAlertAt(final int index) {
    return MineAlertCell.at(index);
  }

  public static Cell emptyAt(final int index) {
    return EmptyCell.at(index);
  }

  protected Cell(final int index, final CellType type) {
    this(index, type, CellStatus.COVERED);
  }

  protected Cell(final int index, final CellType type, final CellStatus status) {
    this.index = index;
    this.type = type;
    this.status = status;
  }

  public CellType type() {
    return type;
  }

  public CellStatus status() {
    return status;
  }

  public int mines() {
    return 0;
  }

  public abstract Cell uncover(UncoveringType uncoveringType);

  public abstract Cell incrementMine();

  public boolean isMine() {
    return false;
  }

  public boolean isUncovered() {
    return status.equals(CellStatus.UNCOVERED);
  }

  public boolean isCovered() {
    return status.equals(CellStatus.COVERED);
  }

  public boolean matchIndex(final int cellIndex) {
    return this.index == cellIndex;
  }

  public int index() {
    return index;
  }

  public abstract boolean shouldPropagateUncovering();

  @Override
  public boolean equals(final Object other) {
    if (this == other) return true;
    if (other == null || !(other instanceof Cell)) return false;
    final Cell cell = (Cell) other;
    return index == cell.index;
  }

  @Override
  public int hashCode() {
    return Objects.hash(index);
  }

  @Override
  public int compareTo(final Cell other) {
    return Integer.compare(index, other.index);
  }
}
