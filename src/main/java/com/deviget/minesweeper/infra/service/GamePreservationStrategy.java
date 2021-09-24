package com.deviget.minesweeper.infra.service;

import com.deviget.minesweeper.domain.model.game.Game;
import com.deviget.minesweeper.domain.model.game.GameId;
import com.deviget.minesweeper.domain.model.user.UserId;
import com.deviget.minesweeper.infra.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("gamePreservationStrategy")
public class GamePreservationStrategy implements GameStatusTransitionStrategy{

  @Autowired
  private GameRepository gameRepository;

  @Override
  public Game changeStatus(final GameId id, final UserId userId, final GameStatusTransition statusTransition) {
    final Game existingGame = gameRepository.findByIdAndUserId(id, userId);
    existingGame.pause();
    return gameRepository.save(existingGame);
  }

}
