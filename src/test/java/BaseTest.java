import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import ru.praktikum.client.ingredient.IngredientClient;
import ru.praktikum.client.order.OrderClient;
import ru.praktikum.client.user.UserClient;
import ru.praktikum.model.order.Order;
import ru.praktikum.model.user.User;
import ru.praktikum.generator.UserGenerator;
import java.util.ArrayList;
import java.util.List;

public class BaseTest {
    protected final UserGenerator generator = new UserGenerator();
    protected final UserClient userClient = new UserClient();
    protected static final String ANSEW = "success";
    protected static final String MESSAGE = "message";
    protected static final String ACCESS_TOKEN = "accessToken";
    protected static final String ORDER = "order";
    protected static final String ORDERS = "orders";
    protected User user;
    protected String accessToken;
    protected Response responseCreateUser;
    protected String name;
    protected String password;
    protected String email;
    protected IngredientClient ingredientClient;
    protected OrderClient orderClient = new OrderClient();
    protected List<String> ingredient_list;
    protected Order order;


    @Before
    public void createUser() {
        user = generator.createUserFull();
        responseCreateUser = userClient.create(user);
        accessToken = responseCreateUser.body().path(ACCESS_TOKEN);
        name = user.getName();
        password = user.getPassword();
        email = user.getEmail();
    }

    @Before
    public void prepereListOrder() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        ingredientClient = new IngredientClient();
        orderClient = new OrderClient();
        ingredient_list = new ArrayList<>();
        order = new Order(ingredient_list);
    }

    @After
    public void deleteUser() {
        if (user != null) {
            userClient.deleteUser(user, accessToken);
        }
    }
}
