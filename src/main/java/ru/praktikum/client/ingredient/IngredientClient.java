package ru.praktikum.client.ingredient;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.client.Client;
import ru.praktikum.model.ingredient.Ingredient;
import static io.restassured.RestAssured.given;

public class IngredientClient extends Client {
    private static final String INGREDIENTS = "/api/ingredients";

    @Step("Получить ингредиент")
    public static Ingredient getIngredient() {
        return given()
                .header("Content-Type", "application/json")
                .log().all()
                .get("/api/ingredients")
                .body()
                .as(Ingredient.class);
    }
}
