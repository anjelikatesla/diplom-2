package com.example;

import com.example.api.client.AuthRegisterClient;
import com.example.api.client.AuthUserClient;
import com.example.api.client.OrdersClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UserOrderListTest extends BaseTest {

    private Ingredient ingredients;

    @Before
    public void createTestUser() {
        ingredients = new Ingredient();
        User user = new User();
        AuthRegisterClient.createUserBeforeTests(user);
    }

    @After
    public void deleteTestUser() {
        AuthUserClient.deleteUser(AuthRegisterClient.userToken);
    }

    @Test
    @DisplayName("Список заказов неавторизованного пользователя")
    public void gatOrderWithoutAuth() {
        String message = OrdersClient
                .userOrderList("")
                .then()
                .assertThat()
                .statusCode(401)
                .and()
                .extract()
                .path("message");
        assertThat(message, equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Список заказов авторизованного пользователя")
    public void getOrderWithAuth() {
        OrdersClient.order(AuthRegisterClient.userToken, OrdersClient.positiveOrderBody(ingredients));
        ArrayList<LinkedHashMap> testBody = OrdersClient
                .userOrderList(AuthRegisterClient.userToken)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .path("orders");
        assertThat(testBody.get(0), notNullValue());
    }
}
