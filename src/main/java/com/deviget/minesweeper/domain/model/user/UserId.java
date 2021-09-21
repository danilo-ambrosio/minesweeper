package com.deviget.minesweeper.domain.model.user;

import java.util.Objects;
import java.util.UUID;

public class UserId {

  public final String value;

  public static UserId create() {
    return new UserId();
  }

  private UserId() {
    this.value = UUID.randomUUID().toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final UserId userId = (UserId) o;
    return Objects.equals(value, userId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }


}
