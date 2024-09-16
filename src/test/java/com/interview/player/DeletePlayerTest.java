package com.interview.player;

import com.interview.base.BaseTest;
import com.interview.client.PlayerClient;
import com.interview.dto.DeletePlayerRequestDto;
import com.interview.dto.GetPlayerRequestDto;
import com.interview.dto.PlayerDto;
import com.interview.enums.UserType;
import com.interview.helper.TestDataHelper;
import com.interview.utils.TestContext;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.Issue;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.interview.constants.StringConstants.PLAYER_ID;

@Epic("Player")
@Feature("Delete player")
public class DeletePlayerTest extends BaseTest {

    private PlayerClient playerClient;

    @BeforeClass
    public void setUpClient() {
        playerClient = new PlayerClient();
    }

    @BeforeMethod
    public void createPlayer() {
        Response response = playerClient.sendCreatePlayerGetRequest(TestDataHelper.createPlayerDtoByUserType(UserType.DEFAULT_SUPERVISOR_USER).getLogin(), TestDataHelper.createRandomPlayerDto());
        TestContext.saveSharedParameter(PLAYER_ID, response.as(PlayerDto.class).getId());
    }

    @Test(description = "Delete player with invalid editor")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("There is success deletion, even with invalid editor")
    public void deletePlayerWithInvalidEditorTest() {
        String editor = RandomStringUtils.randomAlphabetic(15);
        Response response = playerClient.sendDeleteUserRequest(editor,
                DeletePlayerRequestDto.builder().playerId((Long) TestContext.getSharedParameter(PLAYER_ID)).build());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), HttpStatus.SC_FORBIDDEN, "Unexpected status code");
        softAssert.assertEquals(response.getBody().asString(), StringUtils.EMPTY, "Body should be empty");
        softAssert.assertAll();
    }

    @Test(description = "Delete player with valid editor")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("I'd replace 200 status code with 404 in case when not-existing id provided")
    public void deletePlayerWithValidEditorTest() {
        String editor = TestDataHelper.createPlayerDtoByUserType(UserType.DEFAULT_SUPERVISOR_USER).getLogin();
        Response deletePlayerResponse = playerClient.sendDeleteUserRequest(editor,
                DeletePlayerRequestDto.builder().playerId((Long) TestContext.getSharedParameter(PLAYER_ID)).build());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(deletePlayerResponse.getStatusCode(), HttpStatus.SC_NO_CONTENT, "Unexpected status code");
        softAssert.assertEquals(deletePlayerResponse.getBody().asString(), StringUtils.EMPTY, "Body should be empty");
        Response getPlayerResponse = playerClient.sendGetSpecifiedPlayerPostRequest(GetPlayerRequestDto.builder().playerId((Long) TestContext.getSharedParameter(PLAYER_ID)).build());
        softAssert.assertEquals(getPlayerResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND, "Unexpected status code");
        softAssert.assertEquals(getPlayerResponse.getBody().asString(), StringUtils.EMPTY, "Body should be empty");
        softAssert.assertAll();
    }
}
