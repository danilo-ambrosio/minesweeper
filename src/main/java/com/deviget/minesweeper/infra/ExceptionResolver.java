package com.deviget.minesweeper.infra;

import com.deviget.minesweeper.domain.model.user.AuthenticationException;
import com.deviget.minesweeper.domain.model.user.UniqueUsernameException;
import com.deviget.minesweeper.domain.model.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionResolver {

  @ExceptionHandler(value = AuthenticationException.class)
  public ResponseEntity<Object> resolveAuthenticationException(final AuthenticationException authenticationException) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @ExceptionHandler(value = UserNotFoundException.class)
  public ResponseEntity<Object> resolveUserNotFoundException(final UserNotFoundException userNotFoundException) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @ExceptionHandler(value = UniqueUsernameException.class )
  public ResponseEntity<Object> resolveUniqueUsernameException(final UniqueUsernameException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

}
