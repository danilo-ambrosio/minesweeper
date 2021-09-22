package com.deviget.minesweeper.infra.resources.data;

public class CellOperationData {

  public final int rowIndex;
  public final int cellIndex;
  public final String type;

  public CellOperationData(final int rowIndex,
                           final int cellIndex,
                           final String type) {
    this.type = type;
    this.rowIndex = rowIndex;
    this.cellIndex = cellIndex;
  }

}
