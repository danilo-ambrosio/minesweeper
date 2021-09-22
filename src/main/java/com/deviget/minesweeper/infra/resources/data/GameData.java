package com.deviget.minesweeper.infra.resources.data;

import com.deviget.minesweeper.domain.model.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameData {

  public final String id;
  public final String status;
  public final long timeElapsed;
  public final List<RowData> rows = new ArrayList<>();

  public static List<GameData> from(List<Game> games) {
    return games.stream().map(GameData::from).collect(Collectors.toList());
  }

  public static GameData from(final Game game) {
    return new GameData(game);
  }

  private GameData(final Game game) {
    this.id = game.id().value();
    this.timeElapsed = game.updatedOn() - game.startedOn();
    this.status = game.status().toString();
    this.rows.addAll(RowData.from(game.rows()));
  }
}
