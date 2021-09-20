package com.deviget.minesweeper.domain.model.game;

import java.util.UUID;

public class GameId {

  private final String value;

  public static GameId create() {
    return new GameId();
  }

  private GameId() {
    this.value = UUID.randomUUID().toString();
  }
}
