package com.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

public class BaseTest {
    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site";

    @BeforeClass
    public static void setUp() {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .build();
        RestAssured.requestSpecification = requestSpec;
    }
}
