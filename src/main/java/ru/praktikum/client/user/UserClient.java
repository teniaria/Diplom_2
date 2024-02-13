package ru.praktikum.client.user;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.client.Client;
import ru.praktikum.model.user.User;
import ru.praktikum.model.user.UserWithoutEmail;
import ru.praktikum.model.user.UserWithoutName;
import ru.praktikum.model.user.UserWithoutPassword;
import ru.praktikum.model.user.auth.UserForAuth;
import ru.praktikum.model.user.auth.UserForAuthWithoutEmail;
import ru.praktikum.model.user.auth.UserForAuthWithoutPassword;
import java.util.Map;

public class UserClient extends Client {
    private final static String REGISTER = "/auth/register";
    private final static String AUTH = "/auth/login";
    private final static String INFOUSER = "/auth/user";
    public static final String HEADER_AUTHORIZATION = "Authorization";

    @Step("Создание пользователя")
    public Response create(User user) {
        return spec()
                .body(user)
                .when()
                .post(REGISTER);
    }

    @Step("Создание пользователя без поля email")
    public Response createWithoutEmail(UserWithoutEmail userWithoutEmail) {
        return spec()
                .body(userWithoutEmail)
                .when()
                .post(REGISTER);
    }

    @Step("Создание пользователя без поля password")
    public Response createWithoutPassword(UserWithoutPassword userWithoutPassword) {
        return spec()
                .body(userWithoutPassword)
                .when()
                .post(REGISTER);
    }

    @Step("Создание пользователя без поля name")
    public Response createWithoutName(UserWithoutName userWithoutName) {
        return spec()
                .body(userWithoutName)
                .post(REGISTER);
    }

    @Step("Авторизация под существующим пользователем")
    public Response login(UserForAuth userForAuth) {
        return spec()
                .body(userForAuth)
                .when()
                .post(AUTH);
    }

    @Step("Авторизация без поля email")
    public Response loginWithoutEmail(UserForAuthWithoutEmail userForAuthWithoutEmail) {
        return spec()
                .body(userForAuthWithoutEmail)
                .post(AUTH);
    }

    @Step("Авторизация без поля password")
    public Response loginWithoutPassword(UserForAuthWithoutPassword userForAuthWithoutPassword) {
        return spec()
                .body(userForAuthWithoutPassword)
                .post(AUTH);
    }

    @Step("Изменение данных авторизованного пользователя")
    public Response changeInfoUser(Map<String, String> updateData, String accessToken) {
        return spec()
                .header(HEADER_AUTHORIZATION, accessToken)
                .body(updateData)
                .patch(INFOUSER);
    }

    @Step("Изменение данных неавторизованного пользователя")
    public Response changeInfoUserWithoutLogin(Map<String, String> updateData) {
        return spec()
                .body(updateData)
                .patch(INFOUSER);
    }

    @Step("Удаление пользователя")
    public Response deleteUser(User user, String accessToken) {
        return spec()
                .header(HEADER_AUTHORIZATION, accessToken)
                .body(user)
                .delete(INFOUSER);
    }
}
