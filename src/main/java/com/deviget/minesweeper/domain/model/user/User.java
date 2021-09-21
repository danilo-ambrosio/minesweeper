package com.deviget.minesweeper.domain.model.user;

public class User {

  private final UserId userId;
  private final String username;
  private final String password;

  public static User with(final String username, final String password) {
    return new User(username, password);
  }

  private User(final String username,
               final String password) {
    this.userId = UserId.create();
    this.username = username;
    this.password = password;
  }

  public boolean authenticate(final String password) {
    return this.password.equals(password);
  }

}
