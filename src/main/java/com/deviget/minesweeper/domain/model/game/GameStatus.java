// Copyright © 2012-2021 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package com.deviget.minesweeper.domain.model.game;

/**
 * {@code GameStatus} describes the {@link Game} evolution caused
 * by user interactions. Based on {@code GameStatus}, {@link Game}
 * is able to safely change its state and ensure invariants.
 *
 * @author Danilo Ambrosio
 */
public enum GameStatus {
  NEW,
  ONGOING,
  PAUSED,
  WON,
  LOST
}
