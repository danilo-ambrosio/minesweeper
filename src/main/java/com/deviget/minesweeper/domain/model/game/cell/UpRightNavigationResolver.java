package com.deviget.minesweeper.domain.model.game.cell;

import com.deviget.minesweeper.domain.model.game.BoardSize;

public class UpRightNavigationResolver implements CellNavigationResolver {

  @Override
  public CellCoordinate resolve(final BoardSize boardSize, final CellCoordinate currentCoordinate) {
    if (currentCoordinate.isTop() || currentCoordinate.isRightEnd(boardSize)) {
      return CellCoordinate.invalid();
    }
    return CellCoordinate.with(currentCoordinate.rowIndex - 1, currentCoordinate.cellIndex + 1);
  }

}
