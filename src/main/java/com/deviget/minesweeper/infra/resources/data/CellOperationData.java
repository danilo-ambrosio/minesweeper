package com.deviget.minesweeper.infra.resources.data;

public class CellOperationData {

  public final int rowIndex;
  public final int cellIndex;
  public final long timeElapsed;
  public final String type;

  public CellOperationData(final int rowIndex,
                           final int cellIndex,
                           final String type,
                           final long timeElapsed) {
    this.type = type;
    this.rowIndex = rowIndex;
    this.timeElapsed = timeElapsed;
    this.cellIndex = cellIndex;
  }

}
