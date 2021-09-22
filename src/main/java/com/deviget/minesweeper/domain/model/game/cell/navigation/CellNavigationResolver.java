package com.deviget.minesweeper.domain.model.game.cell.navigation;

import com.deviget.minesweeper.domain.model.game.BoardSize;
import com.deviget.minesweeper.domain.model.game.cell.CellCoordinate;

public interface CellNavigationResolver {

  CellCoordinate resolve(final BoardSize boardSize, final CellCoordinate currentCoordinate);

}
