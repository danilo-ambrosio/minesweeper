package com.deviget.minesweeper.infra.resources.data;

import com.deviget.minesweeper.domain.model.game.cell.Cell;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CellData {

  public final int index;
  public final String status;
  public final String type;
  public final int mines;

  public static List<CellData> from(final Set<Cell> cells) {
    return cells.stream().map(CellData::new).collect(Collectors.toList());
  }

  private CellData(final Cell cell) {
    this.index = cell.index();
    this.status = cell.status().name();
    this.type = cell.type().name();
    this.mines = cell.mines();
  }

}
