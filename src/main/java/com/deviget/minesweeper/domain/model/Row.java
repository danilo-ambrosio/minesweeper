package com.deviget.minesweeper.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Row {

  private final int index;
  private final List<Cell> cells = new ArrayList<>();

  private Row(final int index) {
    this.index = index;
  }

}
