package com.deviget.minesweeper.domain.model.game;

import com.deviget.minesweeper.domain.model.UserId;
import com.deviget.minesweeper.domain.model.game.cell.Cell;
import com.deviget.minesweeper.domain.model.game.cell.CellCoordinate;
import com.deviget.minesweeper.domain.model.game.cell.CellStatus;
import com.deviget.minesweeper.domain.model.game.cell.CellType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static com.deviget.minesweeper.domain.model.game.cell.CellType.EMPTY;

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
            cell -> cell.type().equals(EMPTY) &&
                    cell.status().equals(CellStatus.COVERED);

    Assertions.assertTrue(rows.stream().allMatch(row -> row.cells().stream().allMatch(cellsAssertion)));
  }

  @Test
  public void testThatTimestampIsUpdated() throws InterruptedException {
    final Game game = Game.configure(Preferences.with(4, 6, 10), UserId.create());
    wait(200, () -> game.uncoverCell(CellCoordinate.with(2, 3)));
    Assertions.assertTrue(game.startedOn() < game.updatedOn());
  }

  @Test
  public void testThatCellIsUncoveredWhenGameIsNew() {
    final Game game = Game.configure(Preferences.with(4, 6, 10), UserId.create());
    game.uncoverCell(CellCoordinate.with(2, 3));
    Assertions.assertEquals(GameStatus.ONGOING, game.status());
    Assertions.assertEquals(4, game.rows().size());
    Assertions.assertEquals(10, game.rows().stream().flatMap(row -> row.cells().stream()).filter(Cell::isMine).count());
    Assertions.assertEquals(CellStatus.UNCOVERED, game.rows().stream().filter(row -> row.index() == 2).map(row -> row.cellAt(3).status()).findFirst().get());
  }

  @Test
  public void testThatGameStatusIsLost() {
    final UserId userId = UserId.create();
    final Game game = Game.configure(Preferences.with(4, 6, 10), userId);
    game.uncoverCell(CellCoordinate.with(2, 3));
    game.uncoverCell(findCellCoordinates(game, CellType.MINE).get(0));
    Assertions.assertEquals(GameStatus.LOST, game.status());
  }

  @Test
  public void testThatGameStatusIsWon() {
    final UserId userId = UserId.create();
    final Game game = Game.configure(Preferences.with(4, 6, 10), userId);
    game.uncoverCell(CellCoordinate.with(2, 3));
    findCellCoordinates(game, CellType.MINE_ALERT).forEach(game::uncoverCell);
    Assertions.assertEquals(GameStatus.ONGOING, game.status());
    findCellCoordinates(game, EMPTY).forEach(game::uncoverCell);
    Assertions.assertEquals(GameStatus.WON, game.status());
  }

  private List<CellCoordinate> findCellCoordinates(final Game game, final CellType type) {
    final List<CellCoordinate> coordinates = new ArrayList<>();
    for(final Row row : game.rows()) {
      row.cells().stream().filter(cell -> cell.type().equals(type)).forEach(cell -> {
        coordinates.add(CellCoordinate.with(row.index(), cell.index()));
      });
    }
    return coordinates;
  }

  private void wait(final int millis, final Runnable task) throws InterruptedException {
    Thread.sleep(millis);
    task.run();
  }
}