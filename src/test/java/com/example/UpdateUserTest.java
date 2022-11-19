package com.example;

import com.example.api.client.AuthRegisterClient;
import com.example.api.client.AuthUserClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UpdateUserTest extends BaseTest {

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
    @DisplayName("Изменение имени пользователя с авторизацией")
    public void updateNameWithAuthTest() {
        boolean success = AuthUserClient
                .updateUser(AuthRegisterClient.userToken, AuthUserClient.getUserWithoutPasswordAndEmail(user))
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .path("success");
        assertThat(success, equalTo(true));
    }

    @Test
    @DisplayName("Изменение почты пользователя с авторизацией")
    public void updateMailWithAuthTest() {
        boolean success = AuthUserClient
                .updateUser(AuthRegisterClient.userToken, AuthUserClient.getUserForEmailChange(user))
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .path("success");
        assertThat(success, equalTo(true));
    }

    @Test
    @DisplayName("Изменение пароля пользователя без авторизации")
    public void updatePasswordWithoutAuthTest() {
        String message = AuthUserClient
                .updateUser("", AuthUserClient.getUserForPasswordChange(user))
                .then()
                .assertThat()
                .statusCode(401)
                .and()
                .extract()
                .path("message");
        assertThat(message, equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Изменение пароля пользователя с авторизацией")
    public void updatePasswordWithAuthTest() {
        boolean success = AuthUserClient
                .updateUser(AuthRegisterClient.userToken, AuthUserClient.getUserForPasswordChange(user))
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .path("success");
        assertThat(success, equalTo(true));
    }

    @Test
    @DisplayName("Изменение почты пользователя без авторизации")
    public void updateEmailWithoutAuthTest() {
        String message = AuthUserClient
                .updateUser("", AuthUserClient.getUserForEmailChange(user))
                .then()
                .assertThat()
                .statusCode(401)
                .and()
                .extract()
                .path("message");
        assertThat(message, equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Изменение имени пользователя без авторизации")
    public void updateNameWithoutAuthTest() {
        String message = AuthUserClient
                .updateUser("", AuthUserClient.getUserWithoutPasswordAndEmail(user))
                .then()
                .assertThat()
                .statusCode(401)
                .and()
                .extract()
                .path("message");
        assertThat(message, equalTo("You should be authorised"));
    }
}
