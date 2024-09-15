package com.interview.player;

import com.interview.base.BaseTest;
import com.interview.client.PlayerClient;
import com.interview.dto.PlayerDto;
import com.interview.helper.TestDataHelper;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
}
