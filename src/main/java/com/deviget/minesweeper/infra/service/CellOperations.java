package com.deviget.minesweeper.infra.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.deviget.minesweeper.infra.service.CellOperation.*;

@Component
public class CellOperations {

  private final Map<CellOperation, CellOperationStrategy> cellOperationStrategies;

  public CellOperations(@Qualifier("uncoveringOperationStrategy") final CellOperationStrategy uncoveringOperation,
                        @Qualifier("flagPlacementOperationStrategy") final CellOperationStrategy flagPlacementOperation,
                        @Qualifier("clearanceOperationStrategy") final CellOperationStrategy clearanceOperation,
                        @Qualifier("questionMarkPlacementOperationStrategy") final CellOperationStrategy questionMarkPlacementOperation) {
    this.cellOperationStrategies =
            Map.of(UNCOVERING, uncoveringOperation, FLAG_PLACEMENT, flagPlacementOperation,
                    CLEARANCE, clearanceOperation, QUESTION_MARK_PLACEMENT, questionMarkPlacementOperation);
  }

  public CellOperationStrategy of(final CellOperation cellOperation) {
    return cellOperationStrategies.get(cellOperation);
  }

}
