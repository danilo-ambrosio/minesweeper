package com.deviget.minesweeper.domain.model.game;

import com.deviget.minesweeper.domain.model.game.cell.CellCoordinate;
import com.deviget.minesweeper.domain.model.game.cell.CellType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

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
}
