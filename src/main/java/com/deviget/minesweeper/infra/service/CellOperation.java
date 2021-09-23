package com.deviget.minesweeper.infra.service;

import com.deviget.minesweeper.domain.model.game.cell.CellCoordinate;

public class CellOperation {

  public final CellCoordinate cellCoordinate;
  public final CellOperationType type;
  public final long timeElapsed;

  public static CellOperation from(final int rowIndex,
                            final int cellIndex,
                            final String type,
                            final long timeElapsed) {
    return new CellOperation(CellCoordinate.with(rowIndex, cellIndex), CellOperationType.valueOf(type), timeElapsed);
  }

  private CellOperation(final CellCoordinate cellCoordinate,
                        final CellOperationType type,
                        final long timeElapsed) {
    this.cellCoordinate = cellCoordinate;
    this.timeElapsed = timeElapsed;
    this.type = type;
  }
}
