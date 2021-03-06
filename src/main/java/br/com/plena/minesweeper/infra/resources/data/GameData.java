package br.com.plena.minesweeper.infra.resources.data;

import br.com.plena.minesweeper.domain.model.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameData {

  public final String id;
  public final String status;
  public final long startedOn;
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
    this.startedOn = game.startedOn();
    this.timeElapsed = game.timeElapsed();
    this.status = game.status().toString();
    this.rows.addAll(RowData.from(game.rows()));
  }
}
