package com.deviget.minesweeper.domain.model.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

  @Test
  public void testThatUserIsAuthenticated() {
    Assertions.assertTrue(User.with("ms-gamer", "dmsg89").authenticate("dmsg89"));
  }

  @Test
  public void testThatUserIsNotAuthenticated() {
    Assertions.assertFalse(User.with("ms-gamer", "dmsg89").authenticate("123"));
  }

}
