package com.deviget.minesweeper.domain.model.game.cell;

import com.deviget.minesweeper.domain.model.game.BoardSize;

public interface CellNavigationResolver {

  CellCoordinate resolve(final BoardSize boardSize, final CellCoordinate currentCoordinate);

}
