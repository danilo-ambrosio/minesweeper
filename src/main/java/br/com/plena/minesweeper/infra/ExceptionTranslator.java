package br.com.plena.minesweeper.infra;

import br.com.plena.minesweeper.domain.model.user.AuthenticationException;
import br.com.plena.minesweeper.domain.model.user.UniqueUsernameException;
import br.com.plena.minesweeper.domain.model.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * {@code ExceptionTranslator} translate exceptions to HTTP responses.
 *
 * @author Danilo Ambrosio
 */
@ControllerAdvice
public class ExceptionTranslator {

  @ExceptionHandler(value = AuthenticationException.class)
  public ResponseEntity<Object> resolveAuthenticationException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @ExceptionHandler(value = UserNotFoundException.class)
  public ResponseEntity<Object> resolveUserNotFoundException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @ExceptionHandler(value = UniqueUsernameException.class )
  public ResponseEntity<Object> resolveUniqueUsernameException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

}
