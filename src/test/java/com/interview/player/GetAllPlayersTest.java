package com.interview.player;

import com.interview.base.BaseTest;
import com.interview.client.PlayerClient;
import com.interview.dto.ErrorResponseDto;
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

import static com.interview.constants.StringConstants.METHOD_NOT_ALLOWED_ERROR;
import static com.interview.constants.StringConstants.PATH;
import static org.testng.Assert.assertEquals;

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
}
