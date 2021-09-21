package com.deviget.minesweeper.infra.resources;

import com.deviget.minesweeper.infra.resources.data.GameData;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameResourceTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testThatGameIsConfigured() throws Exception {
    final String json = "{\"rows\":6, \"columns\":6, \"numberOfMines\":8}";

    this.mockMvc.perform(post("/games").header("userId", "1").contentType(APPLICATION_JSON_VALUE)
                    .content(json)).andExpect(status().isCreated()).andDo(print())
                    .andExpect(content().string(containsString(expectedConfiguredGameStatus)))
                    .andExpect(content().string(containsString(expectedConfiguredGameRows)));
  }

  @Test
  public void testThatGameIsResumed() throws Exception {
    final String json = "{\"rows\":6, \"columns\":6, \"numberOfMines\":8}";

    final String gameJson =
            this.mockMvc.perform(post("/games").header("userId", "1").contentType(APPLICATION_JSON_VALUE).content(json))
                    .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

    final String gameId = new Gson().fromJson(gameJson, GameData.class).id;

    this.mockMvc.perform(patch("/games/" + gameId).header("userId", "1").contentType(APPLICATION_JSON_VALUE).content(json))
            .andExpect(status().isOk());
  }

  private static final String expectedConfiguredGameStatus = "\"status\":\"NEW\"";

  private static final String expectedConfiguredGameRows =
          "\"rows\":[{\"index\":0,\"cells\":[{\"index\":0,\"status\":\"COVERED\",\"type\":\"EMPTY\",\"mines\":0}," +
                  "{\"index\":1,\"status\":\"COVERED\",\"type\":\"EMPTY\",\"mines\":0}," +
                  "{\"index\":2,\"status\":\"COVERED\",\"type\":\"EMPTY\",\"mines\":0}," +
                  "{\"index\":3,\"status\":\"COVERED\",\"type\":\"EMPTY\",\"mines\":0}," +
                  "{\"index\":4,\"status\":\"COVERED\",\"type\":\"EMPTY\",\"mines\":0}," +
                  "{\"index\":5,\"status\":\"COVERED\",\"type\":\"EMPTY\",\"mines\":0}]}," +
                  "{\"index\":1,\"cells\":[{\"index\":0,\"status\":\"COVERED\",\"type\":\"EMPTY\",\"mines\":0}," +
                  "{\"index\":1,\"status\":\"COVERED\",\"type\":\"EMPTY\",\"mines\":0}," +
                  "{\"index\":2,\"status\":\"COVERED\",\"type\":\"EMPTY\",\"mines\":0}," +
                  "{\"index\":3,\"status\":\"COVERED\",\"type\":\"EMPTY\",\"mines\":0}," +
                  "{\"index\":4,\"status\":\"COVERED\",\"type\":\"EMPTY\",\"mines\":0}," +
                  "{\"index\":5,\"status\":\"COVERED\",\"type\":\"EMPTY\",\"mines\":0}]},";


}
