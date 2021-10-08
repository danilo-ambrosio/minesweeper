package br.com.plena.minesweeper.infra.resources;

import br.com.plena.minesweeper.domain.model.game.Game;
import br.com.plena.minesweeper.domain.model.game.GameId;
import br.com.plena.minesweeper.domain.model.game.Preferences;
import br.com.plena.minesweeper.domain.model.user.UserId;
import br.com.plena.minesweeper.infra.resources.data.CellOperationData;
import br.com.plena.minesweeper.infra.resources.data.GameData;
import br.com.plena.minesweeper.infra.resources.data.GameStatusTransitionData;
import br.com.plena.minesweeper.infra.resources.data.PreferencesData;
import br.com.plena.minesweeper.infra.service.CellOperation;
import br.com.plena.minesweeper.infra.service.GameService;
import br.com.plena.minesweeper.infra.service.GameStatusTransition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * {@code GameResource} exposes {@link Game} operations via REST.
 *
 * @author Danilo Ambrosio
 */
@RestController
@RequestMapping("/games")
public class GameResource {

  @Autowired
  private GameService gameService;

  @PostMapping
  public ResponseEntity<GameData> configure(@RequestBody final PreferencesData preferencesData, final UserId userId) {
    final Preferences preferences = Preferences.with(preferencesData.rows, preferencesData.columns, preferencesData.numberOfMines);
    final Game newGame = gameService.configure(preferences, userId);
    return ResponseEntity.status(HttpStatus.CREATED).body(GameData.from(newGame));
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<GameData> changeStatus(@PathVariable final String id,
                                               @RequestBody final GameStatusTransitionData statusTransitionData,
                                               final UserId userId) {
    final GameStatusTransition statusTransition =
            GameStatusTransition.becauseOf(statusTransitionData.reason);

    final Game existingGame = gameService.changeStatus(GameId.of(id), userId, statusTransition);
    return ResponseEntity.ok(GameData.from(existingGame));
  }

  @PatchMapping("/{id}/cell")
  public ResponseEntity<GameData> performCellOperation(@PathVariable final String id,
                                                       @RequestBody final CellOperationData cellOperationData,
                                                       final UserId userId) {
    final CellOperation cellOperation =
            CellOperation.from(cellOperationData.rowIndex, cellOperationData.cellIndex, cellOperationData.type);

    final Game updatedGame = gameService.performCellOperation(GameId.of(id), userId, cellOperation);

    return ResponseEntity.ok(GameData.from(updatedGame));
  }

  @GetMapping
  public ResponseEntity<List<GameData>> findPaused(final UserId userId) {
    return ResponseEntity.ok(GameData.from(gameService.findPaused(userId)));
  }

}
