package com.deviget.minesweeper.domain.model.game;

import com.deviget.minesweeper.domain.model.game.cell.AdjacentCellCoordinates;
import com.deviget.minesweeper.domain.model.game.cell.Cell;
import com.deviget.minesweeper.domain.model.game.cell.CellCoordinate;
import com.deviget.minesweeper.domain.model.game.cell.UncoveringType;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Row {

  private final int index;
  private final Set<Cell> cells = new TreeSet<>();

  public static Row empty(final int index, final int numberOfCells) {
    return new Row(index, numberOfCells);
  }

  private Row(final int index, final int numberOfCells) {
    this.index = index;
    this.cells.addAll(IntStream.range(0, numberOfCells).mapToObj(Cell::emptyAt).collect(Collectors.toSet()));
  }

  public void placeMine(final int cellIndex) {
    replaceCell(Cell.mineAt(cellIndex));
  }

  public void incrementMineAlert(final int cellIndex) {
    replaceCell(cellAt(cellIndex).incrementMine());
  }

  private void replaceCell(final Cell cell) {
    this.cells.remove(cell);
    this.cells.add(cell);
  }

  public Set<Cell> cells() {
    return Collections.unmodifiableSet(cells);
  }

  Cell cellAt(final int cellIndex) {
    return cells.stream().filter(c -> c.matchIndex(cellIndex)).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unable to find cell with index " + cellIndex));
  }

  public Cell uncoverCell(final Board board,
                          final CellCoordinate cellCoordinate,
                          final UncoveringType uncoveringType) {
    final Cell uncoveredCell =
            cellAt(cellCoordinate.cellIndex()).uncover(uncoveringType);

    replaceCell(uncoveredCell);

    if(uncoveredCell.shouldPropagateUncovering()) {
      AdjacentCellCoordinates.resolve(board.size(), cellCoordinate).forEach(adjacentCoordinate -> {
        board.uncoverCell(adjacentCoordinate, UncoveringType.PROPAGATION);
      });
    }

    return uncoveredCell;
  }

  boolean isCellUncovered(final CellCoordinate cellCoordinate) {
    return cellAt(cellCoordinate.cellIndex()).isUncovered();
  }

  public int index() {
    return index;
  }
}
