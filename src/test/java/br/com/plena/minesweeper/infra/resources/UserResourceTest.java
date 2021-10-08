package br.com.plena.minesweeper.infra.resources;

import br.com.plena.minesweeper.infra.resources.data.UserData;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserResourceTest extends ResourceTest {

  @Test
  public void testThatUserIsRegistered() throws Exception {
    final UserData userData = new UserData("", "ms-user", "@oi127");

    final ResultActions userRegistered =
            this.mockMvc.perform(post("/users").contentType(APPLICATION_JSON_VALUE)
                    .content(asJson(userData))).andExpect(status().isCreated());

    final UserData userRegisteredData = toData(userRegistered, TypeToken.get(UserData.class));

    Assertions.assertNotNull(userRegisteredData.id);
    Assertions.assertEquals(userData.username, userRegisteredData.username);
  }

  @Test
  public void testThatUserWithExistingUsernameIsNotRegistered() throws Exception {
    createSample();

    final UserData userData = new UserData("", "gamer", "@oi127");

    this.mockMvc.perform(post("/users").contentType(APPLICATION_JSON_VALUE)
            .content(asJson(userData))).andExpect(status().isBadRequest());
  }

  @Test
  public void testThatUserIsAuthenticated() throws Exception {
    createSample();

    final ResultActions userAuthenticated =
            this.mockMvc.perform(get("/users?username=gamer&password=_12ygb")
                    .contentType(APPLICATION_JSON_VALUE)).andExpect(status().isOk());

    final UserData userAuthenticatedData = toData(userAuthenticated, TypeToken.get(UserData.class));

    Assertions.assertNotNull(userAuthenticatedData.id);
  }

  @Test
  public void testThatUserIsNotAuthenticated() throws Exception {
    createSample();
    this.mockMvc.perform(get("/users?username=gamer&password=admin123").contentType(APPLICATION_JSON_VALUE))
            .andExpect(status().isUnauthorized());
  }

  private void createSample() throws Exception {
    final UserData userData = new UserData("", "gamer", "_12ygb");
    this.mockMvc.perform(post("/users").contentType(APPLICATION_JSON_VALUE).content(asJson(userData))).andExpect(status().isCreated());
  }

  @AfterEach
  public void tearDown() {
    mongoTemplate.dropCollection("user");
  }

}
