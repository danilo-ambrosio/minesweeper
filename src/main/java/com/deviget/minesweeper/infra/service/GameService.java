package com.deviget.minesweeper.infra.service;

import com.deviget.minesweeper.domain.model.game.Game;
import com.deviget.minesweeper.domain.model.game.GameId;
import com.deviget.minesweeper.domain.model.game.Preferences;
import com.deviget.minesweeper.domain.model.user.UserId;
import com.deviget.minesweeper.infra.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

  @Autowired
  private GameRepository gameRepository;

  public Game configure(final Preferences preferences, final UserId userId) {
    return gameRepository.save(Game.configure(preferences, userId));
  }

  public Game resume(final GameId gameId, final UserId userId) {
    final Game existingGame =
            gameRepository.findByIdAndUserId(gameId, userId);

    existingGame.resume();

    gameRepository.save(existingGame);

    return existingGame;
  }

  public Game find(final GameId gameId, final UserId userId) {
    return gameRepository.findByIdAndUserId(gameId, userId);
  }

}
