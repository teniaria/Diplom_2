import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Test;
import ru.praktikum.model.ingredient.Ingredient;
import java.util.Locale;
import static org.hamcrest.CoreMatchers.equalTo;

public class OrderCreateTest extends BaseTest {
    public static final String MESSAGE_NOT_IDS = "Ingredient ids must be provided";
    public static final String NAME = "name";
    public static final String ORDER_NUMBER = "order.number";
    public static final String ORDER_INGREDIENTS = "order.ingredients";
    public static final String ORDER_ID = "order._id";
    public static final String ORDER_OWNER_NAME = "order.owner.name";
    public static final String ORDER_OWNER_EMAIL = "order.owner.email";
    public static final String ORDER_STATUS = "order.status";
    public static final String ORDER_NAME = "order.name";
    public static final String ORDER_PRICE = "order.price";
    public static final String DONE = "done";
    public static final String ID_INCORRECT = RandomStringUtils.randomAlphanumeric(3);

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Проверка состояния кода и тела ответа")
    public void orderCreateCorrectWithoutLogin() {
        Ingredient ingredient = ingredientClient.getIngredient();
        ingredient_list.add(ingredient.getData().get(1).get_id());
        Response response = orderClient.createOrderWithoutLogin(order);
        response.then().log().all().statusCode(HttpStatus.SC_OK)
                .assertThat().body(ANSEW, Matchers.is(true))
                .and().body(NAME, Matchers.notNullValue())
                .and().body(ORDER_NUMBER, Matchers.any(Integer.class));
    }

    @Test
    @DisplayName("Создание заказа без авторизации, без ингредиентов")
    @Description("Проверка состояния кода и значения для поля success")
    public void orderCreateWithoutIngredientsWithoutLogin() {

        Response response = orderClient.getOrdersWithoutLogin();
        response.then().log().all().statusCode(HttpStatus.SC_BAD_REQUEST | HttpStatus.SC_UNAUTHORIZED)
                .and().body(ANSEW, equalTo(false));
    }

    @Test
    @DisplayName("Создание заказа без авторизации, с некорректным хэшем ингредиентов")
    @Description("Проверка состояния кода")
    public void orderCreateWithIncorrectIngredientsWithoutLogin() {
        Ingredient ingredient = ingredientClient.getIngredient();
        ingredient_list.add(ingredient.getData().get(0).get_id() + ID_INCORRECT);
        Response response = orderClient.createOrderWithoutLogin(order);
        response.then().log().all().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    @Description("Проверка состояния кода и тела ответа")
    public void orderCreateCorrectWithLogin() {
        for (int i = 1; i < 8; i++) {
            Ingredient ingredient = ingredientClient.getIngredient();
            ingredient_list.add(ingredient.getData().get(i).get_id());
        }
        Response response = orderClient.createOrderWithLogin(order, accessToken);
        response.then().log().all().assertThat().statusCode(HttpStatus.SC_OK)
                .and().body(ANSEW, Matchers.is(true))
                .and().body(NAME, Matchers.notNullValue())
                .and().body(ORDER_NUMBER, Matchers.any(Integer.class))
                .and().body(ORDER_INGREDIENTS, Matchers.notNullValue())
                .and().body(ORDER_ID, Matchers.notNullValue())
                .and().body(ORDER_OWNER_NAME, Matchers.is(name))
                .and().body(ORDER_OWNER_EMAIL, Matchers.is(email.toLowerCase(Locale.ROOT)))
                .and().body(ORDER_STATUS, Matchers.is(DONE))
                .and().body(ORDER_NAME, Matchers.notNullValue())
                .and().body(ORDER_PRICE, Matchers.notNullValue());
    }

    @Test
    @DisplayName("Создание заказа с авторизацией, без ингредиентов")
    @Description("Проверка состояния кода и значений для полей: success и message")
    public void orderCreateWithoutIngredientsWithLogin() {
        Response response = orderClient.createOrderWithLogin(order, accessToken);
        response.then().log().all().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .and().body(ANSEW, Matchers.is(false))
                .and().body(MESSAGE, Matchers.is(MESSAGE_NOT_IDS));
    }

    @Test
    @DisplayName("Создание заказа с авторизацией, с некорректным хэшем ингредиентов")
    @Description("Проверка состояния кода")
    public void orderCreateWithIncorrectIngredientsWithLogin() {
        Ingredient ingredient = ingredientClient.getIngredient();
        ingredient_list.add(ingredient.getData().get(0).get_id() + ID_INCORRECT);
        Response response = orderClient.createOrderWithLogin(order, accessToken);
        response.then().log().all().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}
