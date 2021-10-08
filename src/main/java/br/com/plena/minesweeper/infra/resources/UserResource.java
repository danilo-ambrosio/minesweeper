package br.com.plena.minesweeper.infra.resources;

import br.com.plena.minesweeper.domain.model.user.User;
import br.com.plena.minesweeper.infra.resources.data.UserData;
import br.com.plena.minesweeper.infra.service.UserService;
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
