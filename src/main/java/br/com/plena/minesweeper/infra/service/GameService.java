package br.com.plena.minesweeper.infra.service;

import br.com.plena.minesweeper.domain.model.game.Game;
import br.com.plena.minesweeper.domain.model.game.GameId;
import br.com.plena.minesweeper.domain.model.game.GameStatus;
import br.com.plena.minesweeper.domain.model.game.Preferences;
import br.com.plena.minesweeper.domain.model.user.UserId;
import br.com.plena.minesweeper.infra.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@code GameService} is responsible for providing a coarse-grained application
 * service regarding {@link Game}.
 *
 * @author Danilo Ambrosio
 */
@Service
public class GameService {

  @Autowired
  private CellOperations cellOperations;

  @Autowired
  private GameStatusTransitions gameStatusTransitions;

  @Autowired
  private GameRepository gameRepository;

  public Game configure(final Preferences preferences, final UserId userId) {
    return gameRepository.save(Game.configure(preferences, userId));
  }

  public Game changeStatus(final GameId id, final UserId userId, final GameStatusTransition statusTransition) {
    return gameStatusTransitions.of(statusTransition.reason).changeStatus(id, userId, statusTransition);
  }

  public Game performCellOperation(final GameId gameId,
                                   final UserId userId,
                                   final CellOperation operation) {
    return cellOperations.of(operation.type).perform(gameId, userId, operation);
  }

  public List<Game> findPaused(final UserId userId) {
    return gameRepository.findByUserIdAndStatusIn(userId, List.of(GameStatus.PAUSED));
  }
}
