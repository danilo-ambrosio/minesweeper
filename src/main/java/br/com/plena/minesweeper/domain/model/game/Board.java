package br.com.plena.minesweeper.domain.model.game;

import br.com.plena.minesweeper.domain.model.game.cell.Cell;
import br.com.plena.minesweeper.domain.model.game.cell.CellCoordinate;
import br.com.plena.minesweeper.domain.model.game.cell.UncoveringType;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * {@code Board} deals with the Minesweeper rules that depends on
 * the board size. Once it has the state information of all rows
 * and cells available on a given {@link Game}, it is able to
 * support the aggregate root to consistently update {@link Row}
 * and {@link Cell} data.
 *
 * @author Danilo Ambrosio
 */
public class Board {

  private final BoardSize boardSize;
  private final List<Row> rows = new ArrayList<>();

  static Board empty(final Preferences preferences) {
    final List<Row> rowsWithEmptyCells =
            IntStream.range(0, preferences.rows())
                    .mapToObj(rowIndex -> Row.empty(rowIndex, preferences.columns()))
                    .collect(Collectors.toList());

    return new Board(preferences.boardSize(), rowsWithEmptyCells);
  }

  @PersistenceConstructor
  private Board(final BoardSize boardSize, final List<Row> rows) {
    this.boardSize = boardSize;
    this.rows.addAll(rows);
  }

  void placeMines(final Set<CellCoordinate> mineCoordinates) {
    mineCoordinates.forEach(mineCoordinate -> {
      this.rowAt(mineCoordinate.rowIndex()).placeMine(mineCoordinate.cellIndex());
      mineCoordinate.resolveAdjacent(boardSize).forEach(this::incrementMineAlert);
    });
  }

  Cell uncoverCell(final CellCoordinate cellCoordinate, final UncoveringType userRequest) {
    final Row row = rowAt(cellCoordinate.rowIndex());
    if(row.isCellUncovered(cellCoordinate)) {
      return row.cellAt(cellCoordinate.cellIndex());
    }
    return row.uncoverCell(this, cellCoordinate, userRequest);
  }

  void handleGameEnding() {
    this.rows.forEach(row -> row.handleGameEnding(this));
  }

  void placeQuestionMark(final CellCoordinate cellCoordinate) {
    final Row row = rowAt(cellCoordinate.rowIndex());
    final Cell cell = row.cellAt(cellCoordinate.cellIndex());
    if(!cell.isUncovered()) {
      row.placeQuestionMark(cellCoordinate);
    }
  }

  void placeFlag(final CellCoordinate cellCoordinate) {
    final Row row = rowAt(cellCoordinate.rowIndex());
    final Cell cell = row.cellAt(cellCoordinate.cellIndex());
    if(!cell.isUncovered()) {
      row.placeFlag(cellCoordinate);
    }
  }

  void clear(final CellCoordinate cellCoordinate) {
    final Row row = rowAt(cellCoordinate.rowIndex());
    row.clear(cellCoordinate);
  }

  private void incrementMineAlert(final CellCoordinate cellCoordinate) {
    final Row row = rowAt(cellCoordinate.rowIndex());
    row.incrementMineAlert(cellCoordinate);
  }

  public BoardSize size() {
    return boardSize;
  }

  boolean hasCellsToBeUncovered() {
    return rows.stream().flatMap(row -> row.cells().stream()).anyMatch(cell -> cell.isCovered() && !cell.isMine());
  }

  Row rowAt(final int rowIndex) {
    return this.rows.get(rowIndex);
  }

  List<Row> rows() {
    return Collections.unmodifiableList(rows);
  }
}
