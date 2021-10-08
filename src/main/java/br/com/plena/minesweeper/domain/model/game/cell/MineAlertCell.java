package br.com.plena.minesweeper.domain.model.game.cell;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.springframework.data.annotation.PersistenceConstructor;

/**
 * {@code MineAlertCell} informs the total of mines that surround a cell.
 *
 * @author Danilo Ambrosio
 */
@BsonDiscriminator("mineAlertCell")
public class MineAlertCell extends Cell {

  private final int mines;

  public static MineAlertCell at(final int index, final CellStatus status) {
    return at(index, 1, status);
  }

  public static MineAlertCell at(final int index, final int mines, final CellStatus status) {
    return new MineAlertCell(index, mines, CellType.MINE_ALERT, status);
  }

  @PersistenceConstructor
  private MineAlertCell(final int index, final int mines, final CellType type, final CellStatus status) {
    super(index, type, status);
    this.mines = mines;
  }

  @Override
  public Cell uncover(final UncoveringType uncoveringType) {
    return at(index(), mines, CellStatus.UNCOVERED);
  }

  @Override
  public Cell placeQuestionMark() {
    return at(index(), mines, CellStatus.QUESTION_MARKED);
  }

  @Override
  public Cell placeFlag() {
    return at(index(), mines, CellStatus.FLAGGED);
  }

  @Override
  public Cell incrementMine() {
    return at(index(), mines + 1, status());
  }

  @Override
  public Cell clear() {
    if(this.isUncovered()) {
      return this;
    }
    return at(index(), mines, CellStatus.COVERED);
}

  @Override
  public boolean shouldPropagateUncovering() {
    return false;
  }

  @Override
  public int mines() {
    return mines;
  }

  @Override
  public boolean equals(final Object other) {
    return super.equals(other);
  }

}
