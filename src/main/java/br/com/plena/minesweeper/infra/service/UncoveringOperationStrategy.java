package br.com.plena.minesweeper.infra.service;

import br.com.plena.minesweeper.domain.model.game.Game;
import br.com.plena.minesweeper.domain.model.game.GameId;
import br.com.plena.minesweeper.domain.model.user.UserId;
import br.com.plena.minesweeper.infra.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("uncoveringOperationStrategy")
public class UncoveringOperationStrategy implements CellOperationStrategy {

  @Autowired
  private GameRepository gameRepository;

  @Override
  public Game perform(final GameId gameId, final UserId userId, final CellOperation operation) {
    final Game existingGame = gameRepository.findByIdAndUserId(gameId, userId);
    existingGame.uncoverCell(operation.cellCoordinate);
    return gameRepository.save(existingGame);
  }

}
