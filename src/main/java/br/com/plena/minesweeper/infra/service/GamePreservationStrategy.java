package br.com.plena.minesweeper.infra.service;

import br.com.plena.minesweeper.domain.model.game.Game;
import br.com.plena.minesweeper.domain.model.game.GameId;
import br.com.plena.minesweeper.domain.model.user.UserId;
import br.com.plena.minesweeper.infra.repository.GameRepository;
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
