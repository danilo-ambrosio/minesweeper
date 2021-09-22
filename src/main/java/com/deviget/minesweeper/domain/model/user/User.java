package com.deviget.minesweeper.domain.model.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {

  @Id
  private final UserId id;
  private final String username;
  private final String password;

  public static User with(final String username, final String password) {
    return new User(UserId.create(), username, password);
  }

  @PersistenceConstructor
  private User(final UserId id,
               final String username,
               final String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  public UserId id() {
    return id;
  }

  public String username() {
    return username;
  }

}
