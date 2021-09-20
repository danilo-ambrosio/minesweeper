package com.deviget.minesweeper.domain.model.game;

import com.deviget.minesweeper.domain.model.UserId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

public class GameTest {

  @Test
  public void testThatGameIsConfigured() {
    final UserId userId = UserId.create();
    final Preferences preferences = Preferences.with(3, 6, 7);
    final Game game = Game.configure(preferences, userId);

    Assertions.assertNotNull(game.id());
    Assertions.assertEquals(userId, game.userId());
    Assertions.assertEquals(GameStatus.NEW, game.status());
    Assertions.assertEquals(game.startedOn(), game.updatedOn());

    final List<Row> rows = game.rows();

    Assertions.assertEquals(3, rows.size());

    final Predicate<Cell> cellsAssertion =
            cell -> cell.type().equals(CellType.EMPTY) && cell.status().equals(CellStatus.UNCOVERED);

    Assertions.assertTrue(rows.stream().allMatch(row -> row.cells().stream().allMatch(cellsAssertion)));
  }

}
