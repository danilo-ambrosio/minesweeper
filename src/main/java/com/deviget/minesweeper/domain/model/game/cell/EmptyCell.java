package com.deviget.minesweeper.domain.model.game.cell;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.springframework.data.annotation.PersistenceConstructor;

/**
 * {@code EmptyCell} does contain neither mine nor mine alert
 * and, if uncovered, causes adjacent cells to be uncovered
 * as long as it does not contain mines.
 *
 * @author Danilo Ambrosio
 */
@BsonDiscriminator("emptyCell")
public class EmptyCell extends Cell {

  public static Cell at(final int index) {
    return new EmptyCell(index);
  }

  EmptyCell(final int index) {
    this(index, CellStatus.COVERED);
  }

  EmptyCell(final int index, final CellStatus status) {
    this(index, CellType.EMPTY, status);
  }

  @PersistenceConstructor
  private EmptyCell(final int index, final CellType type, final CellStatus status) {
    super(index, type, status);
  }

  @Override
  public Cell uncover(final UncoveringType uncoveringType) {
    return new EmptyCell(index(), CellStatus.UNCOVERED);
  }

  @Override
  public Cell incrementMine() {
    return Cell.mineAlertAt(index(), status());
  }

  @Override
  public Cell placeQuestionMark() {
    return new EmptyCell(index(), CellStatus.QUESTION_MARKED);
  }

  @Override
  public Cell placeFlag() {
    return new EmptyCell(index(), CellStatus.FLAGGED);
  }

  @Override
  public Cell clear() {
    if(this.isUncovered()) {
      return this;
    }
    return new EmptyCell(index(), CellStatus.COVERED);
  }

  @Override
  public boolean isEmpty() {
    return true;
  }

  @Override
  public boolean shouldPropagateUncovering() {
    return true;
  }

  @Override
  public boolean equals(final Object other) {
    return super.equals(other);
  }

}
