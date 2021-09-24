package com.deviget.minesweeper.domain.model.game;

import com.deviget.minesweeper.domain.model.game.cell.Cell;
import com.deviget.minesweeper.domain.model.game.cell.CellCoordinate;
import com.deviget.minesweeper.domain.model.game.cell.UncoveringType;
import com.deviget.minesweeper.domain.model.game.cell.navigation.AdjacentCellCoordinates;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * {@code Row} cooperate with {@link Cell} mainly by
 * handling operations that affect more than one
 * {@link Cell}.
 *
 * @author Danilo Ambrosio
 */
public class Row {

  private final int index;
  @BsonProperty(useDiscriminator = true)
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

  void placeMine(final int cellIndex) {
    final Cell existingCell = cellAt(cellIndex);
    final Cell mine = Cell.mineAt(cellIndex, existingCell.status());
    updateCell(mine);
  }

  void placeFlag(CellCoordinate cellCoordinate) {
    final Cell cell = cellAt(cellCoordinate.cellIndex());
    updateCell(cell.placeFlag());
  }

  void placeQuestionMark(final CellCoordinate cellCoordinate) {
    final Cell cell = cellAt(cellCoordinate.cellIndex());
    updateCell(cell.placeQuestionMark());
  }

  void incrementMineAlert(final CellCoordinate cellCoordinate) {
    final Cell cell = cellAt(cellCoordinate.cellIndex());
    updateCell(cell.incrementMine());
  }

  void clear(final CellCoordinate cellCoordinate) {
    final Cell cell = cellAt(cellCoordinate.cellIndex());
    updateCell(cell.clear());
  }

  Cell uncoverCell(final Board board,
                          final CellCoordinate cellCoordinate,
                          final UncoveringType uncoveringType) {
    return uncoverCell(board, cellAt(cellCoordinate.cellIndex()), uncoveringType);
  }

  void handleGameEnding(final Board board) {
    final Set<Cell> uncoveredCells =
            this.cells.stream().map(cell -> uncoverCell(board, cell, UncoveringType.GAME_ENDING)).collect(Collectors.toSet());

    this.cells.clear();
    this.cells.addAll(uncoveredCells);
  }

  private Cell uncoverCell(final Board board,
                           final Cell cell,
                           final UncoveringType uncoveringType) {
    final Cell uncoveredCell =
            cell.uncover(uncoveringType);

    if(!uncoveringType.isGameEnding()) {
      updateCell(uncoveredCell);

      if(uncoveredCell.shouldPropagateUncovering()) {
        AdjacentCellCoordinates.resolve(board.size(), CellCoordinate.with(index, cell.index()))
                .forEach(adjacentCoordinate -> board.uncoverCell(adjacentCoordinate, UncoveringType.PROPAGATION));
      }
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
