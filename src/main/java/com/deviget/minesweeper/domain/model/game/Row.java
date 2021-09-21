package com.deviget.minesweeper.domain.model.game;

import com.deviget.minesweeper.domain.model.game.cell.AdjacentCellCoordinates;
import com.deviget.minesweeper.domain.model.game.cell.Cell;
import com.deviget.minesweeper.domain.model.game.cell.CellCoordinate;
import com.deviget.minesweeper.domain.model.game.cell.UncoveringType;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Row {

  private final int index;
  private final Set<Cell> cells = new TreeSet<>();

  public static Row empty(final int index, final int numberOfCells) {
    final Set<Cell> emptyCells =
            IntStream.range(0, numberOfCells).mapToObj(Cell::emptyAt).collect(Collectors.toSet());

    return new Row(index, emptyCells);
  }

  @PersistenceConstructor
  private Row(final int index,  final Set<Cell> cells) {
    this.index = index;
    this.cells.addAll(cells);
  }

  public void placeMine(final int cellIndex) {
    final Cell existingCell = cellAt(cellIndex);
    final Cell mine = Cell.mineAt(cellIndex, existingCell.status());
    updateCell(mine);
  }

  public void placeFlag(CellCoordinate cellCoordinate) {
    final Cell cell = cellAt(cellCoordinate.cellIndex());
    updateCell(cell.placeFlag());
  }

  public void placeQuestionMark(final CellCoordinate cellCoordinate) {
    final Cell cell = cellAt(cellCoordinate.cellIndex());
    updateCell(cell.placeQuestionMark());
  }

  public void incrementMineAlert(final CellCoordinate cellCoordinate) {
    final Cell cell = cellAt(cellCoordinate.cellIndex());
    updateCell(cell.incrementMine());
  }

  public Cell uncoverCell(final Board board,
                          final CellCoordinate cellCoordinate,
                          final UncoveringType uncoveringType) {
    final Cell uncoveredCell =
            cellAt(cellCoordinate.cellIndex()).uncover(uncoveringType);

    updateCell(uncoveredCell);

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

  Cell cellAt(final int cellIndex) {
    return cells.stream().filter(c -> c.matchIndex(cellIndex)).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unable to find cell with index " + cellIndex));
  }

  private void updateCell(final Cell cell) {
    this.cells.remove(cell);
    this.cells.add(cell);
  }

  public Set<Cell> cells() {
    return Collections.unmodifiableSet(cells);
  }

  public int index() {
    return index;
  }
}
