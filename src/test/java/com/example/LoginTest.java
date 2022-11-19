package com.example;

import com.example.api.client.AuthLoginClient;
import com.example.api.client.AuthRegisterClient;
import com.example.api.client.AuthUserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTest extends BaseTest {

    private User user;

    @Before
    public void createTestUser() throws InterruptedException {
        user = new User();
        AuthRegisterClient.createUserBeforeTests(user);
    }

    @After
    public void deleteTestUser() {
        AuthUserClient.deleteUser(AuthRegisterClient.userToken);
    }

    @Test
    @DisplayName("Авторизация пользователя")
    public void loginTest() {
        Response response = AuthLoginClient.login(AuthRegisterClient.getRequestBodyForRegistration(user));
        String token = response
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .path("accessToken");
        assertThat(token, notNullValue());
    }

    @Test
    @DisplayName("Авторизация с неправильной почтой")
    public void loginWithWrongEmailTest() {
        Response response = AuthLoginClient.login(AuthLoginClient.getRequestBodyWithBadEmail(user));
        String messageWithBadLogin = response
                .then()
                .assertThat()
                .statusCode(401)
                .and()
                .extract()
                .path("message");
        assertThat(messageWithBadLogin, equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Авторизация с неправильным паролем")
    public void loginWithWrongPasswordTest() {
        Response response = AuthLoginClient.login(AuthLoginClient.getRequestBodyWithBadPassword(user));
        String messageWithBadPassword = response
                .then()
                .assertThat()
                .statusCode(401)
                .and()
                .extract()
                .path("message");
        assertThat(messageWithBadPassword, equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Авторизация с неправильными почтой и паролем")
    public void loginWithWrongEmailAndPasswordTest() {
        Response response = AuthLoginClient.login(AuthLoginClient.getRequestBodyWithBadEmailAndPassword(user));
        String messageWithBadEmailPassword = response
                .then()
                .assertThat()
                .statusCode(401)
                .and()
                .extract()
                .path("message");
        assertThat(messageWithBadEmailPassword, equalTo("email or password are incorrect"));
    }
}
