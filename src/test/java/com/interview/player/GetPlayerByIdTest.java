package com.interview.player;

import com.interview.base.BaseTest;
import com.interview.client.PlayerClient;
import com.interview.dto.PlayerDto;
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
        Response response = playerClient.sendPostGetSpecifiedPlayerRequest(PlayerDto.builder().id(id).build());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), HttpStatus.SC_NOT_FOUND, "Unexpected status code");
        softAssert.assertEquals(response.getBody().asString(), StringUtils.EMPTY, "Body should be empty");
        softAssert.assertAll();
    }
}
