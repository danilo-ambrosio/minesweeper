package com.deviget.minesweeper.infra.repository;

import com.deviget.minesweeper.domain.model.game.Game;
import com.deviget.minesweeper.domain.model.game.GameId;
import com.deviget.minesweeper.domain.model.user.UserId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, GameId> {

  Game findByIdAndUserId(final GameId gameId, final UserId userId);

}
