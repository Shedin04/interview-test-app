package com.interview.client;

import com.interview.config.ConfigurationCollector;
import com.interview.utils.TestContext;
import io.restassured.response.Response;

import static com.interview.constants.StringConstants.RESPONSE;

public class PlayerClient extends BaseApiClient {

    private static final String ENDPOINTS_PLAYER_GET_ALL_PLAYERS_ENDPOINT = "endpoints.player.get-all-players-endpoint";

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
