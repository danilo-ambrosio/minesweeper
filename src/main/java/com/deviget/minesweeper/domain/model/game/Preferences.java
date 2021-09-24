package com.deviget.minesweeper.domain.model.game;

import org.springframework.data.annotation.PersistenceConstructor;

/**
 * {@code Preferences} allow users to control the difficulty
 * of the Minesweeper game by defining how many rows, columns
 * and mines are involved in a {@link Game}.
 *
 * @author Danilo Ambrosio
 */
public class Preferences {

  private final int rows;
  private final int columns;
  private final int numberOfMines;

  public static Preferences with(final int rows,
                                 final int columns,
                                 final int numberOfMines) {
    if(rows < 1) {
      throw new IllegalStateException("Must have more than 1 row");
    }
    if(columns < 1) {
      throw new IllegalStateException("Must have more than 1 column");
    }
    if(columns * rows < 9) {
      throw new IllegalStateException("Must have more than 9 cells");
    }
    if(numberOfMines > columns * rows) {
      throw new IllegalStateException("Mines cannot exceed the cells total");
    }
    return new Preferences(rows, columns, numberOfMines);
  }

  @PersistenceConstructor
  private Preferences(final int rows,
                      final int columns,
                      final int numberOfMines) {
    this.rows = rows;
    this.columns = columns;
    this.numberOfMines = numberOfMines;
  }

  public int rows() {
    return rows;
  }

  public int columns() {
    return columns;
  }

  public int numberOfMines() {
    return numberOfMines;
  }

  public BoardSize boardSize() {
    return new BoardSize(rows, columns);
  }
}
