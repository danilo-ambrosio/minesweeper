package com.deviget.minesweeper.infra.resources;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;

public class ResourceTest {

  @Autowired
  protected Gson gson;

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected MongoTemplate mongoTemplate;

  protected <T> T toData(final ResultActions resultActions, final TypeToken<T> type) throws UnsupportedEncodingException {
    final String responseContent = resultActions.andReturn().getResponse().getContentAsString();
    return gson.fromJson(responseContent, type.getType());
  }

  protected String asJson(final Object object) {
    return gson.toJson(object);
  }

}
