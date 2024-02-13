import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.praktikum.client.order.OrderClient;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderGetTest extends BaseTest {
    private final OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    @Description("Проверка состояния кода и значений для полей: success и order")
    public void getOrdersWithLogin() {
        orderClient.createOrderWithLogin(order, accessToken);

        Response response = orderClient.getOrdersWithLogin(accessToken);
        response.then().log().all().statusCode(HttpStatus.SC_OK)
                .and().body(ANSEW, equalTo(true))
                .and().body(ORDERS, notNullValue());
    }

    @Test
    @DisplayName("Получение заказов неавторизованного пользователя")
    @Description("Проверка состояния кода и значения для поля success")
    public void getOrdersWithoutLogin() {
        Response response = orderClient.getOrdersWithoutLogin();
        response.then().log().all().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().body(ANSEW, equalTo(false));
    }
}
