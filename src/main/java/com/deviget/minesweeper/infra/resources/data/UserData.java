package com.deviget.minesweeper.infra.resources.data;

import com.deviget.minesweeper.domain.model.user.User;

public class UserData {

  public final String id;
  public final String username;
  public final String password;

  public static UserData from(final User user) {
    return new UserData(user.id().value(), user.username(), "");
  }

  public UserData(final String id, final String username, final String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

}
