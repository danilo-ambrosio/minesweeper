package br.com.plena.minesweeper.infra.service;

import br.com.plena.minesweeper.domain.model.game.Game;
import br.com.plena.minesweeper.domain.model.game.GameId;
import br.com.plena.minesweeper.domain.model.user.UserId;

public interface GameStatusTransitionStrategy {

  Game changeStatus(final GameId id, final UserId userId, final GameStatusTransition statusTransition);

}
