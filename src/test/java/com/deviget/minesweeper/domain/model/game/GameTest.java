package com.deviget.minesweeper.domain.model.game;

import com.deviget.minesweeper.domain.model.UserId;
import com.deviget.minesweeper.domain.model.game.cell.Cell;
import com.deviget.minesweeper.domain.model.game.cell.CellCoordinate;
import com.deviget.minesweeper.domain.model.game.cell.CellStatus;
import com.deviget.minesweeper.domain.model.game.cell.CellType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

public class GameTest {

  @Test
  public void testThatGameIsConfigured() {
    final UserId userId = UserId.create();
    final Preferences preferences = Preferences.with(4, 6, 7);
    final Game game = Game.configure(preferences, userId);

    Assertions.assertNotNull(game.id());
    Assertions.assertEquals(userId, game.userId());
    Assertions.assertEquals(GameStatus.NEW, game.status());
    Assertions.assertEquals(game.startedOn(), game.updatedOn());

    final List<Row> rows = game.rows();

    Assertions.assertEquals(4, rows.size());
    Assertions.assertTrue(rows.stream().allMatch(row -> row.cells().size() == 6));

    final Predicate<Cell> cellsAssertion =
            cell -> cell.type().equals(CellType.EMPTY) &&
                    cell.status().equals(CellStatus.UNCOVERED);

    Assertions.assertTrue(rows.stream().allMatch(row -> row.cells().stream().allMatch(cellsAssertion)));
  }

  @Test
  public void testThatCellIsUncoveredWhenGameIsNew() throws InterruptedException {
    final UserId userId = UserId.create();
    final Preferences preferences = Preferences.with(4, 6, 10);
    final Game game = Game.configure(preferences, userId);

    wait(200, () -> game.uncoverCell(CellCoordinate.with(2, 3)));

    Assertions.assertEquals(GameStatus.ONGOING, game.status());
    Assertions.assertTrue(game.startedOn() < game.updatedOn());

    final List<Row> rows = game.rows();

    Assertions.assertEquals(4, rows.size());
    Assertions.assertEquals(10l, rows.stream().flatMap(row -> row.cells().stream()).filter(Cell::isMine).count());
  }


  private void wait(final int millis, final Runnable task) throws InterruptedException {
    Thread.sleep(millis);
    task.run();
  }
}