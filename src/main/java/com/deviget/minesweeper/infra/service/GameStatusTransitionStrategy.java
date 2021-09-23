package com.deviget.minesweeper.infra.service;

import com.deviget.minesweeper.domain.model.game.Game;
import com.deviget.minesweeper.domain.model.game.GameId;
import com.deviget.minesweeper.domain.model.user.UserId;

public interface GameStatusTransitionStrategy {

  Game changeStatus(final GameId id, final UserId userId, final GameStatusTransition statusTransition);

}
