package com.interview.player;

import com.interview.base.BaseTest;
import com.interview.client.PlayerClient;
import com.interview.dto.ErrorResponseDto;
import com.interview.dto.GetPlayerRequestDto;
import com.interview.dto.PlayerDto;
import com.interview.enums.UserType;
import com.interview.helper.TestDataHelper;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.Issue;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.interview.constants.StringConstants.METHOD_NOT_ALLOWED_ERROR;
import static com.interview.constants.StringConstants.PATH;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

@Epic("Player")
@Feature("Get player by id")
public class GetPlayerByIdTest extends BaseTest {

    private PlayerClient playerClient;

    @BeforeClass
    public void setUpClient() {
        playerClient = new PlayerClient();
    }

    @DataProvider
    public Object[] getPlayerWithInvalidIdTestDataProvider() {
        return new Object[]{
                -1L,
                Long.MAX_VALUE,
        };
    }

    @Test(description = "Get specified player with invalid id", dataProvider = "getPlayerWithInvalidIdTestDataProvider")
    @Severity(SeverityLevel.NORMAL)
    @Issue("I'd replace 200 status code with 404 in case when invalid id provided")
    public void getPlayerWithInvalidIdTest(Long id) {
        Response response = playerClient.sendGetSpecifiedPlayerPostRequest(GetPlayerRequestDto.builder().playerId(id).build());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), HttpStatus.SC_NOT_FOUND, "Unexpected status code");
        softAssert.assertEquals(response.getBody().asString(), StringUtils.EMPTY, "Body should be empty");
        softAssert.assertAll();
    }

    @Test(description = "Get specified player with wrong request method")
    @Severity(SeverityLevel.NORMAL)
    public void getPlayerWithWrongRequestMethodTest() {
        Response response = playerClient.sendGetSpecifiedPlayerDeleteRequest(GetPlayerRequestDto.builder().playerId(0L).build());
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

    @Test(description = "Get valid player by id")
    @Severity(SeverityLevel.NORMAL)
    public void getValidPlayerByIdTest() {
        PlayerDto playerToGet = TestDataHelper.createPlayerDtoByUserType(UserType.DEFAULT_ADMIN_USER);
        Response response = playerClient.sendGetSpecifiedPlayerPostRequest(GetPlayerRequestDto.builder().playerId(playerToGet.getId()).build());
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Unexpected status code");
        assertFalse(response.getBody().asString().isEmpty(), "Body should not be empty");
        PlayerDto receivedPlayer = response.as(PlayerDto.class);
        assertEquals(receivedPlayer, playerToGet, "Incorrect player was returned");
    }
}
