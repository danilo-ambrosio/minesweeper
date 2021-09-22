package com.deviget.minesweeper.infra.repository;

import com.deviget.minesweeper.domain.model.user.User;
import com.deviget.minesweeper.domain.model.user.UserId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, UserId> {

  Optional<User> findByUsername(final String username);
  Optional<User> findByUsernameAndPassword(final String username, final String password);

}
