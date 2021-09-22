package com.deviget.minesweeper.domain.model.game.cell.navigation;

import com.deviget.minesweeper.domain.model.game.BoardSize;
import com.deviget.minesweeper.domain.model.game.cell.CellCoordinate;

public class LowNavigationResolver implements CellNavigationResolver {

  @Override
  public CellCoordinate resolve(final BoardSize boardSize,
                                final CellCoordinate currentCoordinate) {
    if(currentCoordinate.isBottom(boardSize)) {
      return CellCoordinate.invalid();
    }
    return CellCoordinate.with(currentCoordinate.rowIndex + 1, currentCoordinate.cellIndex);
  }

}
