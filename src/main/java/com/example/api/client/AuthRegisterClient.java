package com.example.api.client;

import com.example.User;
import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthRegisterClient {

    public static final String REGISTER = "/api/auth/register";

    public static String userToken;

    @Step("Создание пользователя")
    public static Response createUser(String json) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(REGISTER);
    }

    @Step("Создание тела для регистрации")
    public static String getRequestBodyForRegistration(User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    @Step("Получение тела для запроса без пароля")
    public static String getRequestBodyWithoutPasswordForRegistration(User user) {
        user.setPassword(null);
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    @Step("Получение тела для запроса без имени")
    public static String getRequestBodyWithoutNameForRegistration(User user) {
        user.setName(null);
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    @Step("Получение тела для запроса без почты")
    public static String getRequestBodyWithoutEmailForRegistration(User user) {
        user.setEmail(null);
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    public static void createUserBeforeTests(User user) throws InterruptedException {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        createUser(getRequestBodyForRegistration(user));
        userToken = AuthLoginClient.getUserToken(json);
    }
}