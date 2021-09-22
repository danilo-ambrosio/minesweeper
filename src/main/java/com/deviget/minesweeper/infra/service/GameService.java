package com.deviget.minesweeper.infra.service;

import com.deviget.minesweeper.domain.model.game.Game;
import com.deviget.minesweeper.domain.model.game.GameId;
import com.deviget.minesweeper.domain.model.game.GameStatus;
import com.deviget.minesweeper.domain.model.game.Preferences;
import com.deviget.minesweeper.domain.model.game.cell.CellCoordinate;
import com.deviget.minesweeper.domain.model.user.UserId;
import com.deviget.minesweeper.infra.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

  @Autowired
  private CellOperations cellOperations;

  @Autowired
  private GameRepository gameRepository;

  public Game configure(final Preferences preferences, final UserId userId) {
    return gameRepository.save(Game.configure(preferences, userId));
  }

  public Game resume(final GameId gameId, final UserId userId) {
    final Game existingGame = gameRepository.findByIdAndUserId(gameId, userId);
    existingGame.resume();
    return gameRepository.save(existingGame);
  }

  public Game performCellOperation(final GameId gameId,
                                   final UserId userId,
                                   final CellOperation operation,
                                   final CellCoordinate cellCoordinate) {
    return cellOperations.of(operation).perform(gameId, userId, cellCoordinate);
  }

  public List<Game> findUnfinished(final UserId userId) {
    return gameRepository.findByUserIdAndStatusIn(userId, GameStatus.unfinished());
  }
}
