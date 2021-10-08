package br.com.plena.minesweeper.infra.service;

import br.com.plena.minesweeper.domain.model.game.Game;
import br.com.plena.minesweeper.domain.model.game.GameId;
import br.com.plena.minesweeper.domain.model.user.UserId;

public interface CellOperationStrategy {

  Game perform(final GameId gameId, final UserId userId, final CellOperation cellOperation);

}
