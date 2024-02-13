package ru.praktikum.client.order;

import io.qameta.allure.restassured.AllureRestAssured;
import org.hamcrest.Matchers;
import ru.praktikum.model.order.Order;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.client.Client;
import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    private final static String ORDERS = "/orders";
    private final static String API_ORDERS = "/api/orders";
    public static final String HEADER_AUTHORIZATION = "Authorization";

    @Step("Создание заказа c авторизацией")
    public Response createOrderWithLogin(Order order, String accessToken) {
        return given().log().all().filter(new AllureRestAssured())
                .header("Content-Type", "application/json")
                .header(HEADER_AUTHORIZATION, accessToken)
                .body(order)
                .when()
                .post(API_ORDERS);
    }

    @Step("Создание заказа без авторизации")
    public Response createOrderWithoutLogin(Order order) {
        return given().log().all()
                .filter(new AllureRestAssured())
                .header("Content-Type", "application/json")
                .body(order)
                .when()
                .post(API_ORDERS);
    }

    @Step("Получение заказов конкретного пользователя с авторизацией")
    public Response getOrdersWithLogin(String accessToken) {
        return spec()
                .header(HEADER_AUTHORIZATION, accessToken)
                .get(ORDERS);
    }

    @Step("Получение заказов конкретного пользователя без авторизации")
    public Response getOrdersWithoutLogin() {
        return spec()
                .get(ORDERS);
    }
}
