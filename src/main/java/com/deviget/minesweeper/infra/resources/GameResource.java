package com.deviget.minesweeper.infra.resources;

import com.deviget.minesweeper.domain.model.game.Game;
import com.deviget.minesweeper.domain.model.game.GameId;
import com.deviget.minesweeper.domain.model.game.Preferences;
import com.deviget.minesweeper.domain.model.user.UserId;
import com.deviget.minesweeper.infra.resources.data.GameData;
import com.deviget.minesweeper.infra.resources.data.PreferencesData;
import com.deviget.minesweeper.infra.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @PatchMapping("/{id}")
  public ResponseEntity<GameData> resume(@PathVariable final String id, final UserId userId) {
    final Game existingGame = gameService.resume(GameId.of(id), userId);
    return ResponseEntity.ok(GameData.from(existingGame));
  }

}
