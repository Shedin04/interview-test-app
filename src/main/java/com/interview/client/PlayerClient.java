package com.interview.client;

import com.interview.config.ConfigurationCollector;
import com.interview.dto.PlayerDto;
import com.interview.utils.TestContext;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static com.interview.constants.StringConstants.RESPONSE;
import static java.lang.String.format;

public class PlayerClient extends BaseApiClient {

    private static final String ENDPOINTS_PLAYER_CREATE_PLAYER_ENDPOINT = "endpoints.player.create-player-endpoint";
    private static final String ENDPOINTS_PLAYER_GET_ALL_PLAYERS_ENDPOINT = "endpoints.player.get-all-players-endpoint";

    private static final String AGE = "age";
    private static final String GENDER = "gender";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String ROLE = "role";
    private static final String SCREEN_NAME = "screenName";

    public Response sendGetCreatePlayerRequest(String editor, PlayerDto playerDto) {
        Map<String, Object> params = new HashMap<>();
        if (playerDto.getAge() != null) {
            params.put(AGE, playerDto.getAge());
        }
        if (playerDto.getGender() != null) {
            params.put(GENDER, playerDto.getGender());
        }
        if (playerDto.getLogin() != null) {
            params.put(LOGIN, playerDto.getLogin());
        }
        if (playerDto.getPassword() != null) {
            params.put(PASSWORD, playerDto.getPassword());
        }
        if (playerDto.getRole() != null) {
            params.put(ROLE, playerDto.getRole());
        }
        if (playerDto.getScreenName() != null) {
            params.put(SCREEN_NAME, playerDto.getScreenName());
        }
        Response response = getRequest(format(ConfigurationCollector.getProperty(ENDPOINTS_PLAYER_CREATE_PLAYER_ENDPOINT), editor), params);
        TestContext.saveSharedParameter(RESPONSE, response);
        return response;
    }

    public Response sendGetAllPlayersRequest() {
        Response response = getRequest(ConfigurationCollector.getProperty(ENDPOINTS_PLAYER_GET_ALL_PLAYERS_ENDPOINT));
        TestContext.saveSharedParameter(RESPONSE, response);
        return response;
    }

    public Response sendPostAllPlayersRequest() {
        Response response = postRequest(ConfigurationCollector.getProperty(ENDPOINTS_PLAYER_GET_ALL_PLAYERS_ENDPOINT));
        TestContext.saveSharedParameter(RESPONSE, response);
        return response;
    }
}
