package com.example.api.client;

import com.example.User;
import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthUserClient {

    public static final String USER = "/api/auth/user";

    @Step("Редактирование пользователя")
    public static Response updateUser(String token, String body) {
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(token)
                .and()
                .body(body)
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

    @Step("Тело для изменения имени")
    public static String getUserWithoutPasswordAndEmail(User user) {
        user.setPassword(null);
        user.setEmail(null);
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    @Step("Тело для изменения email")
    public static String getUserForEmailChange(User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    @Step("Тело для изменения пароля")
    public static String getUserForPasswordChange(User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }
}
