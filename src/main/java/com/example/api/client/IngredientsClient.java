package com.example.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class IngredientsClient {

    public static final String INGREDIENTS = "/api/ingredients";
    @Step("Получение ингредиентов")
    public static Response ingredient(String token) {
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(token)
                .and()
                .when()
                .get(INGREDIENTS);
    }
}