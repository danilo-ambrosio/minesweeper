package com.deviget.minesweeper.domain.model.game.cell;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.springframework.data.annotation.PersistenceConstructor;

@BsonDiscriminator("mineCell")
public class MineCell extends Cell {

  public static MineCell at(final int index, final CellStatus status) {
    return new MineCell(index, status);
  }

  @PersistenceConstructor
  private MineCell(final int index, final CellStatus status) {
    super(index, CellType.MINE, status);
  }

  @Override
  public Cell incrementMine() {
    return this;
  }

  @Override
  public Cell uncover(final UncoveringType uncoveringType) {
    if(uncoveringType.isPropagation()) {
      return this;
    }
    return new MineCell(index(), CellStatus.UNCOVERED);
  }

  @Override
  public Cell placeQuestionMark() {
    return new MineCell(index(), CellStatus.QUESTION_MARKED);
  }

  @Override
  public Cell placeFlag() {
    return new MineCell(index(), CellStatus.FLAGGED);
  }

  @Override
  public Cell clear() {
    if(this.isUncovered()) {
      return this;
    }
    return new MineCell(index(), CellStatus.COVERED);
  }

  @Override
  public boolean shouldPropagateUncovering() {
    return false;
  }

  @Override
  public boolean isMine() {
    return true;
  }

  @Override
  public boolean equals(final Object other) {
    return super.equals(other);
  }

}
