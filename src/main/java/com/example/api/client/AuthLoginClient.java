package com.example.api.client;

import com.example.User;
import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthLoginClient {

    public static final String LOGIN = "/api/auth/login";

    @Step("Login")
    public static Response login(String json) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(LOGIN);
    }

    @Step("Получение токена клиента")
    public static String getUserToken(String json) {
        Response response = AuthLoginClient.login(json);
        JsonPath jsonPath = new JsonPath(response.asString());
        String token = "test test";
        try {
            token = jsonPath.getString("accessToken");
        } catch (JsonPathException e) {
            if (response.getStatusCode() == 429) {
                throw new RuntimeException("Слишком много запросов.");
            }
            System.out.printf("response: %d, %s, %s%n", response.getStatusCode(), response.getStatusLine(), response.asString());
            System.out.println(e.getMessage());
        }
        return token.split(" ")[1];
    }

    public static String getRequestBodyWithBadEmail(User user) {
        user.setEmail("test@example.com");
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    public static String getRequestBodyWithBadPassword(User user) {
        user.setPassword("password");
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    public static String getRequestBodyWithBadEmailAndPassword(User user) {
        user.setEmail("test@example.com");
        user.setPassword("password");
        Gson gson = new Gson();
        return gson.toJson(user);
    }
}
