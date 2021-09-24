package com.deviget.minesweeper.infra.repository;

import com.deviget.minesweeper.domain.model.game.Game;
import com.deviget.minesweeper.domain.model.game.GameId;
import com.deviget.minesweeper.domain.model.game.GameStatus;
import com.deviget.minesweeper.domain.model.user.UserId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * {@code GameRepository} represents the repository that deals
 * with persistence and query operations on {@link Game}
 *
 * @author Danilo Ambrosio
 */
public interface GameRepository extends MongoRepository<Game, GameId> {

  Game findByIdAndUserId(final GameId gameId, final UserId userId);

  List<Game> findByUserIdAndStatusIn(final UserId userId, final List<GameStatus> unfinished);

}
