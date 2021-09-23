package com.deviget.minesweeper.infra.resources;

import com.deviget.minesweeper.infra.resources.data.CellOperationData;
import com.deviget.minesweeper.infra.resources.data.GameData;
import com.deviget.minesweeper.infra.resources.data.GameStatusTransitionData;
import com.deviget.minesweeper.infra.resources.data.PreferencesData;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameResourceTest extends ResourceTest {

  @Test
  public void testThatGameIsConfigured() throws Exception {
    final PreferencesData preferences = new PreferencesData(6, 10, 8);

    final ResultActions configuredGame =
            this.mockMvc.perform(post("/games").header("userId", "1").contentType(APPLICATION_JSON_VALUE)
                    .content(asJson(preferences))).andExpect(status().isCreated());

    final GameData configuredGameData = toData(configuredGame, TypeToken.get(GameData.class));

    Assertions.assertNotNull(configuredGameData.id);
    Assertions.assertEquals("NEW", configuredGameData.status);
    Assertions.assertEquals(6, configuredGameData.rows.size());
    Assertions.assertEquals(60, configuredGameData.rows.stream().flatMap(row -> row.cells.stream()).filter(cell -> cell.status.equals("COVERED")).count());
    Assertions.assertEquals(0, configuredGameData.timeElapsed);
  }

  @Test
  public void testThatGameIsPaused() throws Exception {
    final GameData firstGame = createGameSample().get(0);

    final GameStatusTransitionData gamePreservation =
            new GameStatusTransitionData("GAME_PRESERVATION", 5000);

    final ResultActions pausedGame =
            this.mockMvc.perform(patch("/games/" + firstGame.id + "/status").header("userId", "2")
                    .contentType(APPLICATION_JSON_VALUE).content(asJson(gamePreservation))).andExpect(status().isOk());

    final GameData pausedGameData = toData(pausedGame, TypeToken.get(GameData.class));

    Assertions.assertEquals("PAUSED", pausedGameData.status);
    Assertions.assertEquals(pausedGameData.timeElapsed - firstGame.timeElapsed, 5000);
  }

  @Test
  public void testThatGameIsResumed() throws Exception {
    final GameData thirdGame = createGameSample().get(2);

    final GameStatusTransitionData gameContinuation =
            new GameStatusTransitionData("GAME_CONTINUATION", 0);

    final ResultActions resumedGame =
            this.mockMvc.perform(patch("/games/" + thirdGame.id + "/status").header("userId", "3")
                    .contentType(APPLICATION_JSON_VALUE).content(asJson(gameContinuation))).andExpect(status().isOk());

    Assertions.assertEquals("ONGOING", toData(resumedGame, TypeToken.get(GameData.class)).status);
  }

  @Test
  public void testThatCellIsUncovered() throws Exception {
    final GameData secondGame = createGameSample().get(1);

    final CellOperationData uncovering = new CellOperationData(8, 3, "UNCOVERING", 4000);

    final ResultActions updatedGame =
            this.mockMvc.perform(patch("/games/" + secondGame.id + "/cell").header("userId", "2")
                    .contentType(APPLICATION_JSON_VALUE).content(asJson(uncovering)))
                    .andExpect(status().isOk());

    final GameData updatedGameData = toData(updatedGame, TypeToken.get(GameData.class));

    Assertions.assertEquals("ONGOING", updatedGameData.status);
    Assertions.assertEquals("UNCOVERED", updatedGameData.rows.get(8).cells.get(3).status);
    Assertions.assertTrue(updatedGameData.rows.stream().flatMap(row -> row.cells.stream()).filter(cell -> cell.type.equals("MINE_ALERT")).allMatch(cell -> cell.mines > 0));
    Assertions.assertEquals(updatedGameData.timeElapsed - secondGame.timeElapsed, 4000);
  }

  @Test
  public void testThatFlagIsPlaced() throws Exception {
    final GameData secondGame = createGameSample().get(1);

    final CellOperationData flagPlacement = new CellOperationData(4, 0, "FLAG_PLACEMENT", 12000);

    final ResultActions updatedGame =
            this.mockMvc.perform(patch("/games/" + secondGame.id + "/cell").header("userId", "2")
                    .contentType(APPLICATION_JSON_VALUE).content(asJson(flagPlacement)))
                    .andExpect(status().isOk());

    final GameData updatedGameData = toData(updatedGame, TypeToken.get(GameData.class));

    Assertions.assertEquals("NEW", updatedGameData.status);
    Assertions.assertEquals("FLAGGED", updatedGameData.rows.get(4).cells.get(0).status);
    Assertions.assertEquals(updatedGameData.timeElapsed - secondGame.timeElapsed, 12000);
  }

  @Test
  public void testThatQuestionMarkIsPlaced() throws Exception {
    final GameData secondGame = createGameSample().get(1);

    final CellOperationData questionMarkPlacement = new CellOperationData(5, 2, "QUESTION_MARK_PLACEMENT", 20000);

    final ResultActions updatedGame =
            this.mockMvc.perform(patch("/games/" + secondGame.id + "/cell").header("userId", "2")
                            .contentType(APPLICATION_JSON_VALUE).content(asJson(questionMarkPlacement)))
                    .andExpect(status().isOk());

    final GameData updatedGameData = toData(updatedGame, TypeToken.get(GameData.class));

    Assertions.assertEquals("NEW", updatedGameData.status);
    Assertions.assertEquals("QUESTION_MARKED", updatedGameData.rows.get(5).cells.get(2).status);
    Assertions.assertEquals(updatedGameData.timeElapsed - secondGame.timeElapsed, 20000);
  }

  @Test
  public void testThatQuestionMarkIsCleared() throws Exception {
    final GameData secondGame = createGameSample().get(1);
    final CellOperationData questionMarkPlacement = new CellOperationData(5, 2, "QUESTION_MARK_PLACEMENT", 5000);
    final CellOperationData clearance = new CellOperationData(5, 2, "CLEARANCE", 4000);

    this.mockMvc.perform(patch("/games/" + secondGame.id + "/cell").header("userId", "2")
                    .contentType(APPLICATION_JSON_VALUE).content(asJson(questionMarkPlacement)))
            .andExpect(status().isOk());

    final ResultActions updatedGame =
        this.mockMvc.perform(patch("/games/" + secondGame.id + "/cell").header("userId", "2")
                        .contentType(APPLICATION_JSON_VALUE).content(asJson(clearance)))
                .andExpect(status().isOk());

    final GameData updatedGameData = toData(updatedGame, TypeToken.get(GameData.class));

    Assertions.assertEquals("NEW", updatedGameData.status);
    Assertions.assertEquals("COVERED", updatedGameData.rows.get(5).cells.get(2).status);
    Assertions.assertEquals(updatedGameData.timeElapsed - secondGame.timeElapsed, 9000);
  }

  @Test
  public void testThatPausedGamesAreRetrieved() throws Exception {
    createGameSample();

    final ResultActions pausedGames =
            this.mockMvc.perform(get("/games").header("userId", "3")
                    .contentType(APPLICATION_JSON_VALUE)).andExpect(status().isOk());

    final List<GameData> pausedGamesData = toData(pausedGames, new TypeToken<>(){});

    Assertions.assertEquals(1, pausedGamesData.size());
  }

  @Test
  public void testThatGameIsNotConfiguredWithoutAuthenticatedUser() throws Exception {
    final PreferencesData preferences = new PreferencesData(6, 10, 8);

    this.mockMvc.perform(post("/games").contentType(APPLICATION_JSON_VALUE)
            .content(asJson(preferences))).andExpect(status().isUnauthorized());
  }

  private List<GameData> createGameSample() throws Exception {
    final PreferencesData firstPreferencesData = new PreferencesData(6, 6, 8);

    final ResultActions firstGame =
            this.mockMvc.perform(post("/games").header("userId", "2").contentType(APPLICATION_JSON_VALUE)
                    .content(asJson(firstPreferencesData))).andExpect(status().isCreated());

    final PreferencesData secondPreferences = new PreferencesData(10, 4, 3);

    final ResultActions secondGame =
            this.mockMvc.perform(post("/games").header("userId", "2").contentType(APPLICATION_JSON_VALUE)
                    .content(asJson(secondPreferences))).andExpect(status().isCreated());

    final PreferencesData thirdPreferences = new PreferencesData(3, 12, 15);

    final ResultActions thirdGame =
            this.mockMvc.perform(post("/games").header("userId", "3").contentType(APPLICATION_JSON_VALUE)
                    .content(asJson(thirdPreferences))).andExpect(status().isCreated());

    final GameData thirdGameData =
            toData(thirdGame, TypeToken.get(GameData.class));

    this.mockMvc.perform(patch("/games/" + thirdGameData.id + "/status").header("userId", "3")
            .contentType(APPLICATION_JSON_VALUE).content(asJson(new GameStatusTransitionData("GAME_PRESERVATION", 5000))))
            .andExpect(status().isOk());

    return List.of(toData(firstGame, TypeToken.get(GameData.class)),
            toData(secondGame, TypeToken.get(GameData.class)), thirdGameData);
  }

  @AfterEach
  public void tearDown() {
    mongoTemplate.dropCollection("game");
  }
}
