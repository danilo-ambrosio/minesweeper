package br.com.plena.minesweeper.infra.resources.data;

import br.com.plena.minesweeper.domain.model.game.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RowData {

  public final int index;
  public final List<CellData> cells = new ArrayList<>();

  public static List<RowData> from(final List<Row> rows) {
    return rows.stream().map(RowData::new).collect(Collectors.toList());
  }

  private RowData(final Row row) {
    this.index = row.index();
    this.cells.addAll(CellData.from(row.cells()));
  }
}
