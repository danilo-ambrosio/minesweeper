package com.deviget.minesweeper.domain.model.game;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Row {

  private final int index;
  private final Set<Cell> cells = new LinkedHashSet<>();

  public static Row empty(final int index, final int numberOfCells) {
    return new Row(index, numberOfCells);
  }

  private Row(final int index, final int numberOfCells) {
    this.index = index;
    this.cells.addAll(IntStream.range(0, numberOfCells).mapToObj(Cell::emptyAt).collect(Collectors.toSet()));
  }

  public void addMineAt(final Board board, final int cellIndex) {

  }

  public void incrementMineAlertAt(final int cellIndex) {

  }

  public Set<Cell> cells() {
    return Collections.unmodifiableSet(cells);
  }

}
