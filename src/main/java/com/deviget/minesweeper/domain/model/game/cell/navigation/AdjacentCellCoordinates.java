package com.deviget.minesweeper.domain.model.game.cell.navigation;

import com.deviget.minesweeper.domain.model.game.BoardSize;
import com.deviget.minesweeper.domain.model.game.cell.CellCoordinate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AdjacentCellCoordinates {

  private final List<CellNavigationResolver> resolvers;

  public static List<CellCoordinate> resolve(final BoardSize boardSize,
                                             final CellCoordinate currentCoordinate) {
    return new AdjacentCellCoordinates().resolvers.stream()
            .map(resolver -> resolver.resolve(boardSize, currentCoordinate))
            .filter(CellCoordinate::isValid).collect(Collectors.toList());
  }

  private AdjacentCellCoordinates() {
    this.resolvers = Arrays.asList(new UpNavigationResolver(),
            new UpRightNavigationResolver(), new RightNavigationResolver(),
            new LowRightNavigationResolver(), new LowNavigationResolver(),
            new LowLeftNavigationResolver(), new LeftNavigationResolver(),
            new UpLeftNavigationResolver());
  }

}
