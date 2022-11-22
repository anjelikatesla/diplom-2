package com.example.api.client;

import com.example.User;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthLoginClient {

    public static final String LOGIN = "/api/auth/login";

    @Step("Login")
    public static Response login(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(LOGIN);
    }

    private static String getAccessTokenFromResponse(Response response) {
        JsonPath jsonPath = new JsonPath(response.asString());
        try {
            String token = jsonPath.getString("accessToken");
            return token.split(" ")[1];
        } catch (JsonPathException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Step("Получение токена клиента")
    public static String getUserToken(User user) {
        Response response = AuthLoginClient.login(user);
        switch (response.getStatusCode()) {
            case 401:
                return null;
            case 429:
                throw new RuntimeException("Слишком много запросов.");
        }
        return getAccessTokenFromResponse(response);
    }
}
