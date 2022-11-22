package com.example.api.client;

import com.example.User;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthRegisterClient {

    public static final String REGISTER = "/api/auth/register";

    public static String userToken;

    @Step("Создание пользователя")
    public static Response createUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(REGISTER);
    }

    public static void createUserBeforeTests(User user) {
        createUser(user);
        userToken = AuthLoginClient.getUserToken(user);
    }
}