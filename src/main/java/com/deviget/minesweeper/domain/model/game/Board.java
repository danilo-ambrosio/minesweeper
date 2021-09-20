package com.deviget.minesweeper.domain.model.game;

import com.deviget.minesweeper.domain.model.game.cell.AdjacentCellCoordinates;
import com.deviget.minesweeper.domain.model.game.cell.Cell;
import com.deviget.minesweeper.domain.model.game.cell.CellCoordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

  private final BoardSize boardSize;
  private final List<Row> rows = new ArrayList<>();

  static Board empty(final Preferences preferences) {
    return new Board(preferences);
  }

  private Board(final Preferences preferences) {
    final List<Row> rowsWithEmptyCells =
            IntStream.range(0, preferences.rows())
                    .mapToObj(rowIndex -> Row.empty(rowIndex, preferences.columns()))
                    .collect(Collectors.toList());

    this.rows.addAll(rowsWithEmptyCells);
    this.boardSize = preferences.boardSize();
  }

  void placeMines(final Set<CellCoordinate> mineCoordinates) {
    mineCoordinates.forEach(mineCoordinate -> {
      rowAt(mineCoordinate.rowIndex()).placeMine(mineCoordinate.cellIndex());
      this.incrementAdjacentMineAlert(mineCoordinate);
    });
  }

  private void incrementAdjacentMineAlert(final CellCoordinate mineCoordinate) {
    AdjacentCellCoordinates.resolve(boardSize, mineCoordinate).forEach(this::incrementMineAlert);
  }

  private void incrementMineAlert(final CellCoordinate cellCoordinate) {
    rowAt(cellCoordinate.rowIndex()).incrementMineAlert(cellCoordinate.cellIndex());
  }

  Row rowAt(final int rowIndex) {
    return this.rows.get(rowIndex);
  }

  List<Row> rows() {
    return Collections.unmodifiableList(rows);
  }

  Cell uncoverCell(final CellCoordinate cellCoordinate) {
    return Cell.emptyAt(0);
  }

  boolean hasOnlyCoveredMineCells() {
    return rows.stream().allMatch(row -> row.cells().stream().allMatch(cell -> !cell.isUncovered() && cell.isMine()));
  }
}
