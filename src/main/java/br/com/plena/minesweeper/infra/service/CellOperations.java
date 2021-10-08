package br.com.plena.minesweeper.infra.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

import static br.com.plena.minesweeper.infra.service.CellOperationType.*;

@Component
public class CellOperations {

  private final Map<CellOperationType, CellOperationStrategy> strategies;

  public CellOperations(@Qualifier("uncoveringOperationStrategy") final CellOperationStrategy uncoveringOperation,
                        @Qualifier("flagPlacementOperationStrategy") final CellOperationStrategy flagPlacementOperation,
                        @Qualifier("clearanceOperationStrategy") final CellOperationStrategy clearanceOperation,
                        @Qualifier("questionMarkPlacementOperationStrategy") final CellOperationStrategy questionMarkPlacementOperation) {
    this.strategies =
            Map.of(UNCOVERING, uncoveringOperation, FLAG_PLACEMENT, flagPlacementOperation,
                    CLEARANCE, clearanceOperation, QUESTION_MARK_PLACEMENT, questionMarkPlacementOperation);
  }

  public CellOperationStrategy of(final CellOperationType cellOperationType) {
    return strategies.get(cellOperationType);
  }

}
