package com.interview.player;

import com.interview.base.BaseTest;
import com.interview.client.PlayerClient;
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
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.assertEquals;

@Epic("Player")
@Feature("Create player")
public class CreatePlayerTest extends BaseTest {

    private PlayerClient playerClient;

    @BeforeClass
    public void setUpClient() {
        playerClient = new PlayerClient();
    }

    @Test(description = "Create player with invalid editor")
    @Severity(SeverityLevel.NORMAL)
    public void createPlayerWithInvalidEditorTest() {
        String editor = RandomStringUtils.randomAlphabetic(15);
        Response response = playerClient.sendCreatePlayerGetRequest(editor, TestDataHelper.createRandomPlayerDto());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), HttpStatus.SC_FORBIDDEN, "Unexpected status code");
        softAssert.assertEquals(response.getBody().asString(), StringUtils.EMPTY, "Body should be empty");
        softAssert.assertAll();
    }

    @Test(description = "Valid player creating")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("Incorrect response when create user. There are null fields")
    public void createPlayerWithValidDataTest() {
        final String editor = TestDataHelper.createPlayerDtoByUserType(UserType.DEFAULT_SUPERVISOR_USER).getLogin();
        final PlayerDto playerToCreateDto = TestDataHelper.createRandomPlayerDto();
        Response response = playerClient.sendCreatePlayerGetRequest(editor, playerToCreateDto);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Unexpected status code");
        PlayerDto createdPlayerDto = response.as(PlayerDto.class);
        playerToCreateDto.setId(createdPlayerDto.getId());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createdPlayerDto, playerToCreateDto, "Incorrect create player response");
        playerClient.sendGetSpecifiedPlayerPostRequest(GetPlayerRequestDto.builder().playerId(createdPlayerDto.getId()).build());
        softAssert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Unexpected status code");
        PlayerDto receivedPlayer = response.as(PlayerDto.class);
        softAssert.assertEquals(receivedPlayer, playerToCreateDto, "Incorrect get player response");
        softAssert.assertAll();
    }
}
