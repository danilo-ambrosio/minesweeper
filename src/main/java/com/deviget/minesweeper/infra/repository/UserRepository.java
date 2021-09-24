package com.deviget.minesweeper.infra.repository;

import com.deviget.minesweeper.domain.model.user.User;
import com.deviget.minesweeper.domain.model.user.UserId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * {@code UserRepository} represents the repository that deals
 * with persistence and query operations on {@link User}
 *
 * @author Danilo Ambrosio
 */
public interface UserRepository extends MongoRepository<User, UserId> {

  Optional<User> findByUsername(final String username);
  Optional<User> findByUsernameAndPassword(final String username, final String password);

}
