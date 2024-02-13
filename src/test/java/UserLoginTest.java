import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.client.user.UserClient;
import ru.praktikum.generator.UserGenerator;
import ru.praktikum.model.user.auth.UserForAuth;
import ru.praktikum.model.user.auth.UserForAuthWithoutEmail;
import ru.praktikum.model.user.auth.UserForAuthWithoutPassword;
import static org.hamcrest.Matchers.equalTo;

public class UserLoginTest extends BaseTest {
    private final UserGenerator generator = new UserGenerator();
    private final UserClient userClient = new UserClient();
    private UserForAuth userForAuth;

    @Before
    public void setUp() {
        user = generator.createUserFull();
        userClient.create(user);
        userForAuth = generator.createUserForAuth(user);

        System.out.println(user);
    }

    @Test
    @DisplayName("Авторизация под существующим пользователем")
    @Description("Проверка состояния кода и значения для поля success")
    public void userCorrectLogin() {
        Response response = userClient.login(userForAuth);
        response.then().log().all().assertThat().statusCode(HttpStatus.SC_OK)
                .and().body(ANSEW, equalTo(true));
    }

    @Test
    @DisplayName("Авторизация c email = null")
    @Description("Проверка состояния кода и значения для поля success")
    public void userWithEmailNull() {
        UserForAuth userForAuthWithEmailNull = generator.createUserForAuthWithEmailNull(user);

        Response response = userClient.login(userForAuthWithEmailNull);
        response.then().log().all().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().body(ANSEW, equalTo(false));
    }

    @Test
    @DisplayName("Авторизация c password = null")
    @Description("Проверка состояния кода и значения для поля success")
    public void userWithPasswordNull() {
        UserForAuth userForAuthWithPasswordNull = generator.createUserForAuthWithPasswordNull(user);

        Response response = userClient.login(userForAuthWithPasswordNull);
        response.then().log().all().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().body(ANSEW, equalTo(false));
    }
    @Test
    @DisplayName("Авторизация без email")
    @Description("Проверка состояния кода и значения для поля success")
    public void userWithoutEmail() {
        UserForAuthWithoutEmail userForAuthWithoutEmail = generator.createUserForAuthWithoutEmail(user);

        Response response = userClient.loginWithoutEmail(userForAuthWithoutEmail);
        response.then().log().all().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().body(ANSEW, equalTo(false));
    }

    @Test
    @DisplayName("Авторизация без password")
    @Description("Проверка состояния кода и значения для поля success")
    public void userWithoutPassword() {
        UserForAuthWithoutPassword userForAuthWithoutPassword = generator.createUserForAuthWithoutPassword(user);

        Response response = userClient.loginWithoutPassword(userForAuthWithoutPassword);
        response.then().log().all().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().body(ANSEW, equalTo(false));
    }

    @Test
    @DisplayName("Авторизация под неверным/незарегистрированным email")
    @Description("Проверка состояния кода и значения для поля success")
    public void userWithIncorrectEmail() {
        UserForAuth userForAuthIncorrectEmail = generator.createUserForAuthWithIncorrectEmail(user);

        Response response = userClient.login(userForAuthIncorrectEmail);
        response.then().log().all().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().body(ANSEW, equalTo(false));
    }

    @Test
    @DisplayName("Авторизация под неверным password")
    @Description("Проверка состояния кода и значения для поля success")
    public void userWithIncorrectPassword() {
        UserForAuth userForAuthIncorrectPassword = generator.createUserForAuthWithIncorrectPassword(user);

        Response response = userClient.login(userForAuthIncorrectPassword);
        response.then().log().all().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().body(ANSEW, equalTo(false));
    }
}
