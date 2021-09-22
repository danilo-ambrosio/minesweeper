package com.deviget.minesweeper.infra.service;

import com.deviget.minesweeper.domain.model.game.Game;
import com.deviget.minesweeper.domain.model.game.GameId;
import com.deviget.minesweeper.domain.model.game.cell.CellCoordinate;
import com.deviget.minesweeper.domain.model.user.UserId;

public interface CellOperationStrategy {

  Game perform(final GameId gameId, final UserId userId, final CellCoordinate cellCoordinate);

}
