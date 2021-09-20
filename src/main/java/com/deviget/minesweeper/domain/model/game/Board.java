package com.deviget.minesweeper.domain.model.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

  private final List<Row> rows = new ArrayList<>();

  static Board empty(final Preferences preferences) {
    return new Board(preferences);
  }

  private Board(final Preferences preferences) {
    final List<Row> rowsWithEmptyCells =
            IntStream.range(0, preferences.rows())
                    .mapToObj(rowIndex -> Row.empty(rowIndex, preferences.columns()))
                    .collect(Collectors.toList());

    this.rows.addAll(rowsWithEmptyCells);
  }

  void redefineCells(final CellCoordinate firstUncoveredCell, final Set<CellCoordinate> mineCoordinates) {

  }

  List<Row> rows() {
    return Collections.unmodifiableList(rows);
  }

}
