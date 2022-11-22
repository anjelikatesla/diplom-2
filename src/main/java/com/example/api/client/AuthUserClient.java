package com.example.api.client;

import com.example.User;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthUserClient {

    public static final String USER = "/api/auth/user";

    @Step("Редактирование пользователя")
    public static Response updateUser(String token, User user) {
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(token)
                .and()
                .body(user)
                .when()
                .patch(USER);
    }

    @Step("Удаление пользователя")
    public static void deleteUser(String token) {
        given()
                .auth().oauth2(token)
                .when()
                .delete(USER);
    }

    @Step("Удаление пользователя")
    public static void deleteUser(User user) {
        String accessToken = AuthLoginClient.getUserToken(user);
        if (accessToken != null) {
            deleteUser(accessToken);
        }
    }
}
