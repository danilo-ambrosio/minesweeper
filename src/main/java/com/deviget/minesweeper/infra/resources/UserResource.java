package com.deviget.minesweeper.infra.resources;

import com.deviget.minesweeper.domain.model.user.User;
import com.deviget.minesweeper.infra.resources.data.UserData;
import com.deviget.minesweeper.infra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * {@code UserResource} exposes {@link User} operations via REST.
 *
 * @author Danilo Ambrosio
 */
@RestController
@RequestMapping("/users")
public class UserResource {

  @Autowired
  private UserService userService;

  @PostMapping
  public ResponseEntity<UserData> register(@RequestBody final UserData userData) {
    final User user = userService.register(User.with(userData.username, userData.password));
    return ResponseEntity.status(HttpStatus.CREATED).body(UserData.from(user));
  }

  @GetMapping
  public ResponseEntity<UserData> authenticate(@RequestParam("username") final String username,
                                               @RequestParam("password") final String password) {
    final User user = userService.authenticate(username, password);
    return ResponseEntity.ok(UserData.from(user));
  }
}
