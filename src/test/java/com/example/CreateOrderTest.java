package com.example;

import com.example.api.client.AuthRegisterClient;
import com.example.api.client.AuthUserClient;
import com.example.api.client.OrdersClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest extends BaseTest {

    private Ingredient ingredients;

    @Before
    public void createTestUser() {
        User user = new User();
        ingredients = new Ingredient();
        AuthRegisterClient.createUserBeforeTests(user);
    }

    @After
    public void tearDown() {
        AuthUserClient.deleteUser(AuthRegisterClient.userToken);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и ингредиентами")
    public void createOrderWithAuthAndIngredients() {
        String name = OrdersClient
                .order(AuthRegisterClient.userToken, OrdersClient.positiveOrderBody(ingredients))
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .path("name");
        assertThat(name, containsString("бургер"));
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuth() {
        OrdersClient
                .createOrderWithoutLogin(OrdersClient.positiveOrderBody(ingredients))
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredients() {
        String message = OrdersClient
                .order(AuthRegisterClient.userToken, OrdersClient.orderBodyWithoutIngredients(ingredients))
                .then()
                .assertThat()
                .statusCode(400)
                .and()
                .extract()
                .path("message");
        assertThat(message, equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и неверными ингредиентами")
    public void createOrderWithAuthAndWrongIngredients() {
        OrdersClient
                .order(AuthRegisterClient.userToken, OrdersClient.negativeOrderBody(ingredients))
                .then()
                .assertThat()
                .statusCode(500);
    }
}
