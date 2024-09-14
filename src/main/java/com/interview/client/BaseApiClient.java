package com.interview.client;

import com.interview.filter.HeadersFilter;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseApiClient {

    public Response getRequest(String url) {
        return given()
                .filters(new HeadersFilter(), new AllureRestAssured())
                .when()
                .log().method()
                .log().uri()
                .log().headers()
                .get(url)
                .then()
                .extract().response();
    }

    public Response getRequest(String url, Map<String, ?> params) {
        return given()
                .filters(new HeadersFilter(), new AllureRestAssured())
                .params(params)
                .when()
                .log().method()
                .log().uri()
                .log().headers()
                .get(url)
                .then()
                .extract().response();
    }

    public Response postRequest(String url, Object body) {
        return given()
                .filters(new HeadersFilter(), new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .log().method()
                .log().body(true)
                .log().headers()
                .post(url)
                .then()
                .extract().response();
    }

    public Response postRequest(String url) {
        return given()
                .filters(new HeadersFilter(), new AllureRestAssured())
                .contentType(ContentType.JSON)
                .when()
                .log().method()
                .log().body(true)
                .log().headers()
                .post(url)
                .then()
                .extract().response();
    }

    public Response deleteRequest(String url, Object body) {
        return given()
                .filters(new HeadersFilter(), new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .log().method()
                .log().body(true)
                .log().headers()
                .delete(url)
                .then()
                .extract().response();
    }
}