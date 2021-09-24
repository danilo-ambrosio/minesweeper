package com.deviget.minesweeper.domain.model.game;

import com.deviget.minesweeper.domain.model.game.cell.Cell;
import com.deviget.minesweeper.domain.model.game.cell.CellCoordinate;
import com.deviget.minesweeper.domain.model.game.cell.CellStatus;
import com.deviget.minesweeper.domain.model.game.cell.CellType;
import com.deviget.minesweeper.domain.model.user.UserId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GameTest {

  @Test
  public void testThatGameIsConfigured() {
    final UserId userId = UserId.create();
    final Preferences preferences = Preferences.with(4, 6, 7);
    final Game game = Game.configure(preferences, userId);

    Assertions.assertNotNull(game.id());
    Assertions.assertEquals(userId, game.userId());
    Assertions.assertEquals(GameStatus.NEW, game.status());
    Assertions.assertTrue(game.startedOn() > 0);
    Assertions.assertEquals(4, game.rows().size());
    Assertions.assertTrue(game.rows().stream().allMatch(row -> row.cells().size() == 6));
    Assertions.assertTrue(game.rows().stream().flatMap(row -> row.cells().stream()).allMatch(cell -> cell.isEmpty() && cell.isCovered()));
  }

  @Test
  public void testThatGameIsPaused()  {
    final Game game = Game.configure(Preferences.with(4, 6, 10), UserId.create());
    game.pause();
    Assertions.assertEquals(GameStatus.PAUSED, game.status());
  }

  @Test
  public void testThatGameIsNotPaused()  {
    final Game game = Game.configure(Preferences.with(4, 6, 10), UserId.create());
    game.pause();
    Assertions.assertThrows(IllegalStateException.class, game::pause);
  }

  @Test
  public void testThatGameIsResumed()  {
    final Game game = Game.configure(Preferences.with(4, 6, 10), UserId.create());
    game.pause();
    game.resume();
    Assertions.assertEquals(GameStatus.ONGOING, game.status());
  }

  @Test
  public void testThatGameIsNotResumed()  {
    final Game game = Game.configure(Preferences.with(4, 6, 10), UserId.create());
    Assertions.assertThrows(IllegalStateException.class, game::resume);
  }

  @Test
  public void testThatTimeElapses() throws InterruptedException {
    final Game game = Game.configure(Preferences.with(4, 6, 10), UserId.create());
    wait(1000, game::pause);
    wait(1000, game::resume);
    wait(1000, game::pause);
    Assertions.assertEquals(2, game.timeElapsed());
  }

  @Test
  public void testThatIndicatorsArePlaced() {
    final Game game = Game.configure(Preferences.with(4, 6, 10), UserId.create());
    game.placeFlag(CellCoordinate.with(1, 1));
    game.placeQuestionMark(CellCoordinate.with(2, 2));
    game.uncoverCell(CellCoordinate.with(0, 5));
    game.placeFlag(CellCoordinate.with(0, 5));
    game.placeQuestionMark(CellCoordinate.with(0, 5));
    game.placeFlag(CellCoordinate.with(3, 4));
    game.placeQuestionMark(CellCoordinate.with(3, 3));

    final List<Row> rows = game.rows();

    Assertions.assertEquals(CellStatus.UNCOVERED, rows.get(0).cellAt(5).status());
    Assertions.assertEquals(CellStatus.FLAGGED, rows.get(1).cellAt(1).status());
    Assertions.assertEquals(CellStatus.QUESTION_MARKED, rows.get(2).cellAt(2).status());
    Assertions.assertEquals(CellStatus.QUESTION_MARKED, rows.get(3).cellAt(3).status());
    Assertions.assertEquals(CellStatus.FLAGGED, rows.get(3).cellAt(4).status());
  }

  @Test
  public void testThatIndicatorsAreCleared() {
    final Game game = Game.configure(Preferences.with(4, 6, 10), UserId.create());
    game.placeFlag(CellCoordinate.with(1, 1));
    game.placeQuestionMark(CellCoordinate.with(2, 2));
    game.uncoverCell(CellCoordinate.with(0, 5));
    game.placeFlag(CellCoordinate.with(0, 5));
    game.placeQuestionMark(CellCoordinate.with(0, 5));
    game.placeFlag(CellCoordinate.with(3, 4));
    game.placeQuestionMark(CellCoordinate.with(3, 3));

    game.clearCell(CellCoordinate.with(1, 1));
    game.clearCell(CellCoordinate.with(2, 2));
    game.clearCell(CellCoordinate.with(0, 5));
    game.clearCell(CellCoordinate.with(0, 5));
    game.clearCell(CellCoordinate.with(0, 5));
    game.clearCell(CellCoordinate.with(3, 4));
    game.clearCell(CellCoordinate.with(3, 3));

    final List<Row> rows = game.rows();

    Assertions.assertEquals(CellStatus.UNCOVERED, rows.get(0).cellAt(5).status());
    Assertions.assertEquals(CellStatus.COVERED, rows.get(1).cellAt(1).status());
    Assertions.assertEquals(CellStatus.COVERED, rows.get(2).cellAt(2).status());
    Assertions.assertEquals(CellStatus.COVERED, rows.get(3).cellAt(3).status());
    Assertions.assertEquals(CellStatus.COVERED, rows.get(3).cellAt(4).status());
  }

  @Test
  public void testThatCellIsUncoveredWhenGameIsNew() {
    final Game game = Game.configure(Preferences.with(4, 6, 10), UserId.create());
    game.uncoverCell(CellCoordinate.with(2, 3));
    Assertions.assertEquals(GameStatus.ONGOING, game.status());
    Assertions.assertEquals(4, game.rows().size());
    Assertions.assertEquals(10, game.rows().stream().flatMap(row -> row.cells().stream()).filter(Cell::isMine).count());
    Assertions.assertEquals(CellStatus.UNCOVERED, game.rows().get(2).cellAt(3).status());
  }

  @Test
  public void testThatGameStatusIsLost() {
    final Game game = Game.configure(Preferences.with(4, 60, 1), UserId.create());
    game.uncoverCell(CellCoordinate.with(2, 3));
    game.uncoverCell(findCellCoordinates(game, CellType.MINE).get(0));
    Assertions.assertEquals(GameStatus.LOST, game.status());
    Assertions.assertTrue(game.rows().stream().flatMap(row -> row.cells().stream()).allMatch(Cell::isUncovered));
  }

  @Test
  public void testThatGameStatusIsWon() {
    final Game game = Game.configure(Preferences.with(4, 6, 2), UserId.create());
    game.uncoverCell(CellCoordinate.with(2, 3));
    findCellCoordinates(game, CellType.EMPTY).forEach(game::uncoverCell);
    findCellCoordinates(game, CellType.MINE_ALERT).forEach(game::uncoverCell);
    Assertions.assertEquals(GameStatus.WON, game.status());
    Assertions.assertTrue(game.rows().stream().flatMap(row -> row.cells().stream()).allMatch(Cell::isUncovered));
  }

  private List<CellCoordinate> findCellCoordinates(final Game game, final CellType type) {
    final List<CellCoordinate> coordinates = new ArrayList<>();
    game.rows().forEach(row -> row.cells().stream().filter(cell -> cell.type().equals(type))
            .forEach(cell -> coordinates.add(CellCoordinate.with(row.index(), cell.index()))));
    return coordinates;
  }

  private void wait(final int millis, final Runnable task) throws InterruptedException {
    Thread.sleep(millis);
    task.run();
  }
}