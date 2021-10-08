package br.com.plena.minesweeper.infra.service;

import br.com.plena.minesweeper.domain.model.game.cell.CellCoordinate;

public class CellOperation {

  public final CellCoordinate cellCoordinate;
  public final CellOperationType type;

  public static CellOperation from(final int rowIndex,
                                   final int cellIndex,
                                   final String type) {
    return new CellOperation(CellCoordinate.with(rowIndex, cellIndex), CellOperationType.valueOf(type));
  }

  private CellOperation(final CellCoordinate cellCoordinate,
                        final CellOperationType type) {
    this.cellCoordinate = cellCoordinate;
    this.type = type;
  }
}
