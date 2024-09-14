package com.interview.player;

import com.interview.base.BaseTest;
import com.interview.client.PlayerClient;
import com.interview.dto.ErrorResponseDto;
import com.interview.dto.GetAllPlayersResponseDto;
import com.interview.dto.PlayerDto;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static com.interview.constants.StringConstants.METHOD_NOT_ALLOWED_ERROR;
import static com.interview.constants.StringConstants.PATH;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

@Epic("Player")
@Feature("Get all players")
public class GetAllPlayersTest extends BaseTest {

    protected PlayerClient playerClient;

    @BeforeClass
    public void setUpClient() {
        playerClient = new PlayerClient();
    }

    @Test(description = "Get all players request with invalid request method")
    @Severity(SeverityLevel.NORMAL)
    public void getAllPlayersWithInvalidRequestMethodTest() {
        Response response = playerClient.sendPostAllPlayersRequest();
        assertEquals(response.getStatusCode(), HttpStatus.SC_METHOD_NOT_ALLOWED, "Unexpected status code");
        ErrorResponseDto errorResponseDto = response.as(ErrorResponseDto.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(errorResponseDto.getTimestamp(), "Timestamp should not be null");
        softAssert.assertEquals(errorResponseDto.getStatus(), HttpStatus.SC_METHOD_NOT_ALLOWED, "Status code mismatch");
        softAssert.assertEquals(errorResponseDto.getError(), METHOD_NOT_ALLOWED_ERROR, "Error message mismatch");
        softAssert.assertEquals(errorResponseDto.getMessage(), StringUtils.EMPTY, "Message should be empty");
        softAssert.assertEquals(errorResponseDto.getPath(), response.path(PATH), "Path mismatch");
        softAssert.assertAll();
    }

    @Test(description = "Valid getting all players")
    @Severity(SeverityLevel.NORMAL)
    public void validGettingAllPlayersTest() {
        Response response = playerClient.sendGetAllPlayersRequest();
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Unexpected status code");
        GetAllPlayersResponseDto getAllPlayersResponseDto = response.as(GetAllPlayersResponseDto.class);
        List<PlayerDto> players = getAllPlayersResponseDto.getPlayers();
        assertFalse(players.isEmpty(), "Player list should not be empty");
        SoftAssert softAssert = new SoftAssert();
        players.forEach(player -> {
            softAssert.assertNotNull(player.getId(), "Player ID should not be null");
            softAssert.assertTrue(player.getId() > 0, "Player ID should be positive");
            softAssert.assertNotNull(player.getScreenName(), "Screen name should not be null");
            softAssert.assertNotNull(player.getGender(), "Gender should not be null");
            softAssert.assertNotNull(player.getAge(), "Age should not be null");
            softAssert.assertTrue(player.getAge() > 0, "Age should be more than 0");
        });
        softAssert.assertAll();
    }
}
