package br.com.plena.minesweeper.infra.service;

import br.com.plena.minesweeper.domain.model.user.AuthenticationException;
import br.com.plena.minesweeper.domain.model.user.UniqueUsernameException;
import br.com.plena.minesweeper.domain.model.user.User;
import br.com.plena.minesweeper.infra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * {@code UserService} is responsible for providing a coarse-grained application
 * service regarding {@link User}.
 *
 * @author Danilo Ambrosio
 */
@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User register(final User user) {
    final Optional<User> existingUser = userRepository.findByUsername(user.username());
    if(existingUser.isPresent()) {
      throw new UniqueUsernameException();
    }
    return userRepository.save(user);
  }

  public User authenticate(final String username, final String password) {
    final Optional<User> existingUser = userRepository.findByUsernameAndPassword(username, password);
    if(existingUser.isEmpty()) {
      throw new AuthenticationException();
    }
    return existingUser.get();
  }
}
