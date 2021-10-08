package br.com.plena.minesweeper.infra.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GameStatusTransitions {

  private final Map<GameStatusTransition.Reason, GameStatusTransitionStrategy> strategies;

  public GameStatusTransitions(@Qualifier("gameContinuationStrategy") final GameStatusTransitionStrategy gameContinuationStrategy,
                               @Qualifier("gamePreservationStrategy") final GameStatusTransitionStrategy gamePreservationStrategy) {
    this.strategies =
            Map.of(GameStatusTransition.Reason.GAME_CONTINUATION, gameContinuationStrategy,
                    GameStatusTransition.Reason.GAME_PRESERVATION, gamePreservationStrategy);
  }

  public GameStatusTransitionStrategy of(final GameStatusTransition.Reason reason) {
    return strategies.get(reason);
  }

}
