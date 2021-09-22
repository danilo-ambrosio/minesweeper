package com.deviget.minesweeper.domain.model.game.cell.navigation;

import com.deviget.minesweeper.domain.model.game.BoardSize;
import com.deviget.minesweeper.domain.model.game.cell.CellCoordinate;

public class LeftNavigationResolver implements CellNavigationResolver {

  @Override
  public CellCoordinate resolve(final BoardSize boardSize,
                                final CellCoordinate currentCoordinate) {
    if(currentCoordinate.isLeftEnd()) {
      return CellCoordinate.invalid();
    }
    return CellCoordinate.with(currentCoordinate.rowIndex, currentCoordinate.cellIndex - 1);
  }
}
