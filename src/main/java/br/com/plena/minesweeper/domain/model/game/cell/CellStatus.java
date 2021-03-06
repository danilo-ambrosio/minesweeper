package br.com.plena.minesweeper.domain.model.game.cell;

import br.com.plena.minesweeper.domain.model.game.Game;

/**
 * {@code CellStatus} qualifies the state of {@link Cell}
 * after user takes an action during the {@link Game}.
 *
 * @author Danilo Ambrosio
 */
public enum CellStatus {
  COVERED,
  UNCOVERED,
  FLAGGED,
  QUESTION_MARKED
}
