package com.deviget.minesweeper.infra.service;

import com.deviget.minesweeper.domain.model.game.Game;
import com.deviget.minesweeper.domain.model.game.GameId;
import com.deviget.minesweeper.domain.model.user.UserId;
import com.deviget.minesweeper.infra.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("questionMarkPlacementOperationStrategy")
public class QuestionMarkPlacementOperationStrategy implements CellOperationStrategy {

  @Autowired
  private GameRepository gameRepository;

  @Override
  public Game perform(final GameId gameId, final UserId userId, final CellOperation operation) {
    final Game existingGame = gameRepository.findByIdAndUserId(gameId, userId);
    existingGame.placeQuestionMark(operation.cellCoordinate);
    return gameRepository.save(existingGame);
  }

}
