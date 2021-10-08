package br.com.plena.minesweeper.infra.resources.data;

public class PreferencesData {

  public final int rows;
  public final int columns;
  public final int numberOfMines;

  public PreferencesData(final int rows,
                         final int columns,
                         final int numberOfMines) {
    this.rows = rows;
    this.columns = columns;
    this.numberOfMines = numberOfMines;
  }

}
