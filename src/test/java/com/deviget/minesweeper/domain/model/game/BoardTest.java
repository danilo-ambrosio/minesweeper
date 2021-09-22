package com.deviget.minesweeper.domain.model.game;

import com.deviget.minesweeper.domain.model.game.cell.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class BoardTest {

  @Test
  public void testThatMinesArePlaced() {
    final Board board = Board.empty(Preferences.with(7, 5, 6));

    final Set<CellCoordinate> minesCoordinates =
            Set.of(CellCoordinate.with(1, 1),
                    CellCoordinate.with(1, 2),
                    CellCoordinate.with(5, 1),
                    CellCoordinate.with(5, 2),
                    CellCoordinate.with(5, 3),
                    CellCoordinate.with(5, 4));

    board.placeMines(minesCoordinates);

    final List<Row> rows = board.rows();

    Assertions.assertEquals(7, rows.size());
    Assertions.assertTrue(rows.stream().allMatch(row -> row.cells().size() == 5));

    final Predicate<Cell> cellsAssertion =
            cell -> cell.status().equals(CellStatus.COVERED);

    Assertions.assertTrue(rows.stream().allMatch(row -> row.cells().stream().allMatch(cellsAssertion)));

    final Row firstRow = board.rowAt(0);

    Assertions.assertEquals(1, firstRow.cellAt(0).mines());
    Assertions.assertEquals(2, firstRow.cellAt(1).mines());
    Assertions.assertEquals(2, firstRow.cellAt(2).mines());
    Assertions.assertEquals(1, firstRow.cellAt(3).mines());
    Assertions.assertEquals(CellType.EMPTY, firstRow.cellAt(4).type());

    final Row secondRow = board.rowAt(1);

    Assertions.assertEquals(1, secondRow.cellAt(0).mines());
    Assertions.assertEquals(CellType.MINE, secondRow.cellAt(1).type());
    Assertions.assertEquals(CellType.MINE, secondRow.cellAt(2).type());
    Assertions.assertEquals(1, secondRow.cellAt(3).mines());
    Assertions.assertEquals(CellType.EMPTY, secondRow.cellAt(4).type());

    final Row thirdRow = board.rowAt(2);

    Assertions.assertEquals(1, thirdRow.cellAt(0).mines());
    Assertions.assertEquals(2, thirdRow.cellAt(1).mines());
    Assertions.assertEquals(2, thirdRow.cellAt(2).mines());
    Assertions.assertEquals(1, thirdRow.cellAt(3).mines());
    Assertions.assertEquals(CellType.EMPTY, thirdRow.cellAt(4).type());

    final Row fourthRow = board.rowAt(3);

    Assertions.assertEquals(CellType.EMPTY, fourthRow.cellAt(0).type());
    Assertions.assertEquals(CellType.EMPTY, fourthRow.cellAt(1).type());
    Assertions.assertEquals(CellType.EMPTY, fourthRow.cellAt(2).type());
    Assertions.assertEquals(CellType.EMPTY, fourthRow.cellAt(3).type());
    Assertions.assertEquals(CellType.EMPTY, fourthRow.cellAt(4).type());

    final Row fifthRow = board.rowAt(4);

    Assertions.assertEquals(1, fifthRow.cellAt(0).mines());
    Assertions.assertEquals(2, fifthRow.cellAt(1).mines());
    Assertions.assertEquals(3, fifthRow.cellAt(2).mines());
    Assertions.assertEquals(3, fifthRow.cellAt(3).mines());
    Assertions.assertEquals(2, fifthRow.cellAt(4).mines());

    final Row sixthRow = board.rowAt(5);

    Assertions.assertEquals(1, sixthRow.cellAt(0).mines());
    Assertions.assertEquals(CellType.MINE, sixthRow.cellAt(1).type());
    Assertions.assertEquals(CellType.MINE, sixthRow.cellAt(2).type());
    Assertions.assertEquals(CellType.MINE, sixthRow.cellAt(3).type());
    Assertions.assertEquals(CellType.MINE, sixthRow.cellAt(4).type());

    final Row seventhRow = board.rowAt(6);

    Assertions.assertEquals(1, seventhRow.cellAt(0).mines());
    Assertions.assertEquals(2, seventhRow.cellAt(1).mines());
    Assertions.assertEquals(3, seventhRow.cellAt(2).mines());
    Assertions.assertEquals(3, seventhRow.cellAt(3).mines());
    Assertions.assertEquals(2, seventhRow.cellAt(4).mines());
  }

  @Test
  public void testThatCellsAreUncoveredWithPropagation() {
    final Board board = Board.empty(Preferences.with(5, 6, 3));

    final Set<CellCoordinate> minesCoordinates =
            Set.of(CellCoordinate.with(1, 1), CellCoordinate.with(1, 5), CellCoordinate.with(3, 5));

    board.placeMines(minesCoordinates);

    final List<Row> rows = board.rows();

    Assertions.assertEquals(5, rows.size());
    Assertions.assertTrue(rows.stream().allMatch(row -> row.cells().size() == 6));

    final Predicate<Cell> cellsAssertion =
            cell -> cell.status().equals(CellStatus.COVERED);

    Assertions.assertTrue(rows.stream().allMatch(row -> row.cells().stream().allMatch(cellsAssertion)));

    board.uncoverCell(CellCoordinate.with(3, 1), UncoveringType.USER_REQUEST);

    final Row firstRow = board.rowAt(0);

    Assertions.assertEquals(1, firstRow.cellAt(0).mines());
    Assertions.assertEquals(CellStatus.COVERED, firstRow.cellAt(0).status());

    Assertions.assertEquals(1, firstRow.cellAt(1).mines());
    Assertions.assertEquals(CellStatus.COVERED, firstRow.cellAt(1).status());

    Assertions.assertEquals(1, firstRow.cellAt(2).mines());
    Assertions.assertEquals(CellStatus.UNCOVERED, firstRow.cellAt(2).status());

    Assertions.assertEquals(CellType.EMPTY, firstRow.cellAt(3).type());
    Assertions.assertEquals(CellStatus.UNCOVERED, firstRow.cellAt(3).status());

    Assertions.assertEquals(1, firstRow.cellAt(4).mines());
    Assertions.assertEquals(CellStatus.UNCOVERED, firstRow.cellAt(4).status());

    Assertions.assertEquals(1, firstRow.cellAt(5).mines());
    Assertions.assertEquals(CellStatus.COVERED, firstRow.cellAt(5).status());

    final Row secondRow = board.rowAt(1);

    Assertions.assertEquals(1, secondRow.cellAt(0).mines());
    Assertions.assertEquals(CellStatus.COVERED, secondRow.cellAt(0).status());

    Assertions.assertEquals(CellType.MINE, secondRow.cellAt(1).type());
    Assertions.assertEquals(CellStatus.COVERED, secondRow.cellAt(1).status());

    Assertions.assertEquals(1, secondRow.cellAt(2).mines());
    Assertions.assertEquals(CellStatus.UNCOVERED, secondRow.cellAt(2).status());

    Assertions.assertEquals(CellType.EMPTY, secondRow.cellAt(3).type());
    Assertions.assertEquals(CellStatus.UNCOVERED, secondRow.cellAt(3).status());

    Assertions.assertEquals(1, secondRow.cellAt(4).mines());
    Assertions.assertEquals(CellStatus.UNCOVERED, secondRow.cellAt(4).status());

    Assertions.assertEquals(CellType.MINE, secondRow.cellAt(5).type());
    Assertions.assertEquals(CellStatus.COVERED, secondRow.cellAt(5).status());

    final Row thirdRow = board.rowAt(2);

    Assertions.assertEquals(1, thirdRow.cellAt(0).mines());
    Assertions.assertEquals(CellStatus.UNCOVERED, thirdRow.cellAt(0).status());

    Assertions.assertEquals(1, thirdRow.cellAt(1).mines());
    Assertions.assertEquals(CellStatus.UNCOVERED, thirdRow.cellAt(1).status());

    Assertions.assertEquals(1, thirdRow.cellAt(2).mines());
    Assertions.assertEquals(CellStatus.UNCOVERED, thirdRow.cellAt(2).status());

    Assertions.assertEquals(CellType.EMPTY, thirdRow.cellAt(3).type());
    Assertions.assertEquals(CellStatus.UNCOVERED, thirdRow.cellAt(3).status());

    Assertions.assertEquals(2, thirdRow.cellAt(4).mines());
    Assertions.assertEquals(CellStatus.UNCOVERED, thirdRow.cellAt(4).status());

    Assertions.assertEquals(2, thirdRow.cellAt(5).mines());
    Assertions.assertEquals(CellStatus.COVERED, thirdRow.cellAt(5).status());

    final Row fourthRow = board.rowAt(3);

    Assertions.assertEquals(CellType.EMPTY, fourthRow.cellAt(0).type());
    Assertions.assertEquals(CellStatus.UNCOVERED, fourthRow.cellAt(0).status());

    Assertions.assertEquals(CellType.EMPTY, fourthRow.cellAt(1).type());
    Assertions.assertEquals(CellStatus.UNCOVERED, fourthRow.cellAt(1).status());

    Assertions.assertEquals(CellType.EMPTY, fourthRow.cellAt(2).type());
    Assertions.assertEquals(CellStatus.UNCOVERED, fourthRow.cellAt(2).status());

    Assertions.assertEquals(CellType.EMPTY, fourthRow.cellAt(3).type());
    Assertions.assertEquals(CellStatus.UNCOVERED, fourthRow.cellAt(3).status());

    Assertions.assertEquals(1, fourthRow.cellAt(4).mines());
    Assertions.assertEquals(CellStatus.UNCOVERED, fourthRow.cellAt(4).status());

    Assertions.assertEquals(CellType.MINE, fourthRow.cellAt(5).type());
    Assertions.assertEquals(CellStatus.COVERED, fourthRow.cellAt(5).status());

    final Row fifthRow = board.rowAt(4);

    Assertions.assertEquals(CellType.EMPTY, fifthRow.cellAt(0).type());
    Assertions.assertEquals(CellStatus.UNCOVERED, fifthRow.cellAt(0).status());

    Assertions.assertEquals(CellType.EMPTY, fifthRow.cellAt(1).type());
    Assertions.assertEquals(CellStatus.UNCOVERED, fifthRow.cellAt(1).status());

    Assertions.assertEquals(CellType.EMPTY, fifthRow.cellAt(2).type());
    Assertions.assertEquals(CellStatus.UNCOVERED, fifthRow.cellAt(2).status());

    Assertions.assertEquals(CellType.EMPTY, fifthRow.cellAt(3).type());
    Assertions.assertEquals(CellStatus.UNCOVERED, fifthRow.cellAt(3).status());

    Assertions.assertEquals(1, fifthRow.cellAt(4).mines());
    Assertions.assertEquals(CellStatus.UNCOVERED, fifthRow.cellAt(4).status());

    Assertions.assertEquals(1, fifthRow.cellAt(5).mines());
    Assertions.assertEquals(CellStatus.COVERED, fifthRow.cellAt(5).status());
  }

  @Test
  public void testThatCellsAreUncoveredWithoutPropagation() {
    final Board board = Board.empty(Preferences.with(5, 6, 3));

    final Set<CellCoordinate> minesCoordinates =
            Set.of(CellCoordinate.with(1, 1), CellCoordinate.with(1, 5), CellCoordinate.with(3, 5));

    board.placeMines(minesCoordinates);

    final List<Row> rows = board.rows();

    Assertions.assertEquals(5, rows.size());
    Assertions.assertTrue(rows.stream().allMatch(row -> row.cells().size() == 6));

    final Predicate<Cell> cellsAssertion =
            cell -> cell.status().equals(CellStatus.COVERED);

    Assertions.assertTrue(rows.stream().allMatch(row -> row.cells().stream().allMatch(cellsAssertion)));

    board.uncoverCell(CellCoordinate.with(2, 2), UncoveringType.USER_REQUEST);
    board.uncoverCell(CellCoordinate.with(1, 1), UncoveringType.USER_REQUEST);

    Assertions.assertEquals(2, rows.stream().flatMap(row -> row.cells().stream()).filter(Cell::isUncovered).count());
    Assertions.assertEquals(CellStatus.UNCOVERED, rows.get(1).cellAt(1).status());
    Assertions.assertEquals(CellStatus.UNCOVERED, rows.get(2).cellAt(2).status());
  }
}
