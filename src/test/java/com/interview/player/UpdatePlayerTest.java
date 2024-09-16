package com.interview.player;

import com.interview.base.BaseTest;
import com.interview.client.PlayerClient;
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
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.interview.constants.StringConstants.PLAYER_ID;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

@Epic("Player")
@Feature("Update player")
public class UpdatePlayerTest extends BaseTest {

    private PlayerClient playerClient;

    @BeforeClass
    public void setUpClient() {
        playerClient = new PlayerClient();
    }

    @Test(description = "Update player with invalid editor")
    @Severity(SeverityLevel.NORMAL)
    public void updatePlayerWithInvalidEditorTest() {
        String editor = RandomStringUtils.randomAlphabetic(15);
        PlayerDto playerToUpdateDto = TestDataHelper.createRandomPlayerDto();
        playerToUpdateDto.setScreenName(RandomStringUtils.randomAlphabetic(15));
        Response response = playerClient.sendUpdatePlayerPatchRequest(editor, playerToUpdateDto.getId(), playerToUpdateDto);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), HttpStatus.SC_FORBIDDEN, "Unexpected status code");
        softAssert.assertEquals(response.getBody().asString(), StringUtils.EMPTY, "Body should be empty");
        softAssert.assertAll();
    }

    @Test(description = "Update player with valid editor")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("Body is not returned when updating user")
    public void updatePlayerWithValidEditorTest() {
        String editor = TestDataHelper.createPlayerDtoByUserType(UserType.DEFAULT_SUPERVISOR_USER).getLogin();
        PlayerDto playerToUpdateDto = TestDataHelper.createPlayerDtoByUserType(UserType.DEFAULT_ADMIN_USER);
        playerToUpdateDto.setScreenName(RandomStringUtils.randomAlphabetic(15));
        Response updatePlayerResponse = playerClient.sendUpdatePlayerPatchRequest(editor, playerToUpdateDto.getId(), playerToUpdateDto);
        assertEquals(updatePlayerResponse.getStatusCode(), HttpStatus.SC_OK, "Unexpected status code");
        assertNotEquals(updatePlayerResponse.getBody().asString(), StringUtils.EMPTY, "Body shouldn't be empty");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(updatePlayerResponse.as(PlayerDto.class), playerToUpdateDto);
        Response getPlayerResponse = playerClient.sendGetSpecifiedPlayerPostRequest(GetPlayerRequestDto.builder().playerId((Long) TestContext.getSharedParameter(PLAYER_ID)).build());
        softAssert.assertEquals(getPlayerResponse.getStatusCode(), HttpStatus.SC_OK, "Unexpected status code");
        softAssert.assertEquals(getPlayerResponse.as(PlayerDto.class), playerToUpdateDto);
        softAssert.assertAll();
    }
}
