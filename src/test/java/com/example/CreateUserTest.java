package com.example;

import com.example.api.client.AuthRegisterClient;
import com.example.api.client.AuthUserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest extends BaseTest {

    private User user;

    @Before
    public void before() {
        user = new User();
    }

    @After
    public void tearDown() {
        AuthUserClient.deleteUser(user);
    }

    @Test
    @DisplayName("Создание пользователя")
    public void createUserTest() {
        Response response = AuthRegisterClient.createUser(user);
        response
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("Создание уже зарегистрированного пользователя")
    public void createTheSameUserTest() {
        AuthRegisterClient.createUser(user);
        Response response = AuthRegisterClient.createUser(user);
        response
                .then()
                .assertThat()
                .statusCode(403)
                .and()
                .assertThat()
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя без имени")
    public void createUserWithoutNameTest() {
        user.setName(null);
        Response response = AuthRegisterClient.createUser(user);
        String messageWithoutName = response
                .then()
                .assertThat()
                .statusCode(403)
                .and()
                .extract()
                .path("message");
        assertThat(messageWithoutName, equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без почты")
    public void createUserWithoutEmailTest() {
        user.setEmail(null);
        Response response = AuthRegisterClient.createUser(user);
        String messageWithoutEmail = response
                .then()
                .assertThat()
                .statusCode(403)
                .and()
                .extract()
                .path("message");
        assertThat(messageWithoutEmail, equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    public void createUserWithoutPasswordTest() {
        user.setPassword(null);
        Response response = AuthRegisterClient.createUser(user);
        String messageWithoutPassword =
                response
                        .then()
                        .assertThat()
                        .statusCode(403)
                        .and()
                        .extract()
                        .path("message");
        assertThat(messageWithoutPassword, equalTo("Email, password and name are required fields"));
    }
}
