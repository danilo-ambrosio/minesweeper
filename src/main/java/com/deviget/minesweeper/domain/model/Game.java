package com.deviget.minesweeper.domain.model;

public class Game {

  private final GameStatus status;
  private final Preferences preferences;

  private Game(final Preferences preferences) {
    this.status = GameStatus.NEW;
    this.preferences = preferences;
  }




}
