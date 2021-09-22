package com.deviget.minesweeper.infra.resources;

import com.deviget.minesweeper.infra.resources.data.CellOperationData;
import com.deviget.minesweeper.infra.resources.data.GameData;
import com.deviget.minesweeper.infra.resources.data.PreferencesData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameResourceTest {

  @Autowired
  private MockMvc mockMvc;
  private Gson gson = new Gson();

  @Test
  public void testThatGameIsConfigured() throws Exception {
    final PreferencesData preferences = new PreferencesData(6, 10, 8);

    final ResultActions configuredGame =
            this.mockMvc.perform(post("/games").header("userId", "1").contentType(APPLICATION_JSON_VALUE)
                    .content(asJson(preferences))).andDo(print()).andExpect(status().isCreated());

    final GameData configuredGameData = toGameData(configuredGame);

    Assertions.assertNotNull(configuredGameData.id);
    Assertions.assertEquals("NEW", configuredGameData.status);
    Assertions.assertEquals(6, configuredGameData.rows.size());
    Assertions.assertEquals(60, configuredGameData.rows.stream().flatMap(row -> row.cells.stream()).filter(cell -> cell.status.equals("COVERED")).count());
    Assertions.assertEquals(0, configuredGameData.timeElapsed);
  }

  @Test
  public void testThatGameIsResumed() throws Exception {
    final GameData firstGame = createGameSample().get(0);

    final ResultActions resumedGame =
            this.mockMvc.perform(patch("/games/" + firstGame.id).header("userId", "2")
                    .contentType(APPLICATION_JSON_VALUE)).andExpect(status().isOk());

    Assertions.assertTrue(firstGame.timeElapsed < toGameData(resumedGame).timeElapsed);
  }

  @Test
  public void testThatUnfinishedGamesAreRetrieved() throws Exception {
    createGameSample();

    final ResultActions unfinishedGames =
            this.mockMvc.perform(get("/games").header("userId", "2")
                    .contentType(APPLICATION_JSON_VALUE)).andExpect(status().isOk());

    final List<GameData> unfinishedGamesData = toGamesData(unfinishedGames);

    Assertions.assertEquals(2, unfinishedGamesData.size());
  }

  @Test
  public void testThatCellIsUncovered() throws Exception {
    final GameData secondGame = createGameSample().get(1);

    final CellOperationData cellOperationData = new CellOperationData(8, 3, "UNCOVERING");

    final ResultActions updatedGame =
            this.mockMvc.perform(patch("/games/" + secondGame.id + "/cell").header("userId", "2")
                    .contentType(APPLICATION_JSON_VALUE).content(asJson(cellOperationData)))
                    .andExpect(status().isOk());

    final GameData updatedGameData = toGameData(updatedGame);

    Assertions.assertEquals("ONGOING", updatedGameData.status);
    Assertions.assertEquals("UNCOVERED", updatedGameData.rows.get(8).cells.get(3).status);
  }

  @Test
  public void testThatFlagIsPlaced() throws Exception {
    final GameData secondGame = createGameSample().get(1);

    final CellOperationData cellOperationData = new CellOperationData(4, 0, "FLAG_PLACEMENT");

    final ResultActions updatedGame =
            this.mockMvc.perform(patch("/games/" + secondGame.id + "/cell").header("userId", "2")
                    .contentType(APPLICATION_JSON_VALUE).content(asJson(cellOperationData)))
                    .andExpect(status().isOk());

    final GameData updatedGameData = toGameData(updatedGame);

    Assertions.assertEquals("NEW", updatedGameData.status);
    Assertions.assertEquals("FLAGGED", updatedGameData.rows.get(4).cells.get(0).status);
  }

  @Test
  public void testThatQuestionMarkIsPlaced() throws Exception {
    final GameData secondGame = createGameSample().get(1);

    final CellOperationData cellOperationData = new CellOperationData(5, 2, "QUESTION_MARK_PLACEMENT");

    final ResultActions updatedGame =
            this.mockMvc.perform(patch("/games/" + secondGame.id + "/cell").header("userId", "2")
                            .contentType(APPLICATION_JSON_VALUE).content(asJson(cellOperationData)))
                    .andExpect(status().isOk());

    final GameData updatedGameData = toGameData(updatedGame);

    Assertions.assertEquals("NEW", updatedGameData.status);
    Assertions.assertEquals("QUESTION_MARKED", updatedGameData.rows.get(5).cells.get(2).status);
  }

  private List<GameData> createGameSample() throws Exception {
    final PreferencesData firstPreferencesData = new PreferencesData(6, 6, 8);

    final ResultActions firstGame =
            this.mockMvc.perform(post("/games").header("userId", "2").contentType(APPLICATION_JSON_VALUE)
                    .content(asJson(firstPreferencesData))).andExpect(status().isCreated());

    final PreferencesData secondPreferences = new PreferencesData(10, 4, 12);

    final ResultActions secondGame =
            this.mockMvc.perform(post("/games").header("userId", "2").contentType(APPLICATION_JSON_VALUE)
                    .content(asJson(secondPreferences))).andExpect(status().isCreated());

    final PreferencesData thirdPreferences = new PreferencesData(3, 12, 15);

    final ResultActions thirdGame =
            this.mockMvc.perform(post("/games").header("userId", "3").contentType(APPLICATION_JSON_VALUE)
                    .content(asJson(thirdPreferences))).andExpect(status().isCreated());

    return List.of(toGameData(firstGame), toGameData(secondGame), toGameData(thirdGame));
  }

  private GameData toGameData(final ResultActions resultActions) throws UnsupportedEncodingException {
    final String responseContent = resultActions.andReturn().getResponse().getContentAsString();
    return gson.fromJson(responseContent, GameData.class);
  }

  private List<GameData> toGamesData(final ResultActions resultActions) throws UnsupportedEncodingException {
    final String responseContent = resultActions.andReturn().getResponse().getContentAsString();
    return gson.fromJson(responseContent, new TypeToken<List<GameData>>(){}.getType());
  }

  private String asJson(final Object object) {
    return gson.toJson(object);
  }

}
