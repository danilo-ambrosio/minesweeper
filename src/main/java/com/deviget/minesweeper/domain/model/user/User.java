package com.deviget.minesweeper.domain.model.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {

  @Id
  private final UserId userId;
  private final String username;
  private final String password;

  public static User with(final String username, final String password) {
    return new User(username, password);
  }

  @PersistenceConstructor
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
