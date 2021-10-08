package br.com.plena.minesweeper.domain.model.game;

import java.util.Objects;
import java.util.UUID;

/**
 * {@code GameId} contains a value that identifies a {@link Game}.
 *
 * @author Danilo Ambrosio
 */
public class GameId {

  private final String value;

  public static GameId create() {
    return new GameId(UUID.randomUUID().toString());
  }

  public static GameId of(final String value) {
    return new GameId(value);
  }

  public GameId(final String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GameId gameId = (GameId) o;
    return Objects.equals(value, gameId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
