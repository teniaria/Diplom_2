import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Test;
import ru.praktikum.client.user.UserClient;
import ru.praktikum.generator.UserGenerator;
import ru.praktikum.model.user.UserWithoutEmail;
import ru.praktikum.model.user.UserWithoutName;
import ru.praktikum.model.user.UserWithoutPassword;
import static org.hamcrest.Matchers.equalTo;

public class UserCreateTest extends BaseTest {
    private final UserGenerator generator = new UserGenerator();
    private final UserClient userClient = new UserClient();
    private static final String USER_EMAIL = "user.email";
    private static final String USER_NAME = "user.name";
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String REFRESH_TOKEN = "refreshToken";
    private static final String INVALID_USER = "Email, password and name are required fields";
    private static final String EXIST_USER = "User already exists";


    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Проверка состояния кода и значений полей: success и остальных полей != null")
    public void userCreateOriginal() {
        user = generator.createUserFull();

        Response response = userClient.create(user);
        response.then().log().all().assertThat().statusCode(HttpStatus.SC_OK)
                .and().body(ANSEW, equalTo(true))
                .and().body(USER_EMAIL, Matchers.notNullValue())
                .and().body(USER_NAME, Matchers.notNullValue())
                .and().body(ACCESS_TOKEN, Matchers.notNullValue())
                .and().body(REFRESH_TOKEN, Matchers.notNullValue());
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Description("Проверка состояния кода и значения полей: success и message")
    public void userCreateDuplicateEmail() {
        user = generator.createUserFull();

        Response response = userClient.create(user);
        Response conflictResponse = userClient.create(user);
        conflictResponse.then().log().all().statusCode(HttpStatus.SC_FORBIDDEN)
                .and().body(ANSEW, equalTo(false))
                .and().body(MESSAGE, Matchers.is(EXIST_USER));
    }

    @Test
    @DisplayName("Создание пользователя с email = null")
    @Description("Проверка состояния кода и значения полей: success и message")
    public void userCreateWithEmailNull() {
        user = generator.createUserWithEmailNull();

        Response response = userClient.create(user);
        response.then().log().all().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .and().body(ANSEW, equalTo(false))
                .and().body(MESSAGE, Matchers.is(INVALID_USER));
    }

    @Test
    @DisplayName("Создание пользователя с password = null")
    @Description("Проверка состояния кода и значения полей: success и message")
    public void userCreateWithPasswordNull() {
        user = generator.createUserWithPasswordNull();

        Response response = userClient.create(user);
        response.then().log().all().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .and().body(ANSEW, equalTo(false))
                .and().body(MESSAGE, Matchers.is(INVALID_USER));
    }

    @Test
    @DisplayName("Создание пользователя с name = null")
    @Description("Проверка состояния кода и значения полей: success и message")
    public void userCreateWithNameNull() {
        user = generator.createUserWithNameNull();

        Response response = userClient.create(user);
        response.then().log().all().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .and().body(ANSEW, equalTo(false))
                .and().body(MESSAGE, Matchers.is(INVALID_USER));
    }

    @Test
    @DisplayName("Создание пользователя без поля email")
    @Description("Проверка состояния кода и значения полей: success и message")
    public void userCreateWithoutEmail() {
        UserWithoutEmail userWithoutEmail = generator.createUserWithoutEmail();

        Response response = userClient.createWithoutEmail(userWithoutEmail);
        response.then().log().all().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .and().body(ANSEW, equalTo(false))
                .and().body(MESSAGE, Matchers.is(INVALID_USER));
    }

    @Test
    @DisplayName("Создание пользователя без поля password")
    @Description("Проверка состояния кода и значения полей: success и message")
    public void userCreateWithoutPassword() {
        UserWithoutPassword userWithoutPassword= generator.createUserWithoutPassword();

        Response response = userClient.createWithoutPassword(userWithoutPassword);
        response.then().log().all().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .and().body(ANSEW, equalTo(false))
                .and().body(MESSAGE, Matchers.is(INVALID_USER));
    }

    @Test
    @DisplayName("Создание пользователя без поля name")
    @Description("Проверка состояния кода и значения полей: success и message")
    public void userCreateWithoutName() {
        UserWithoutName userWithoutName= generator.createUserWithoutName();

        Response response = userClient.createWithoutName(userWithoutName);
        response.then().log().all().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .and().body(ANSEW, equalTo(false))
                .and().body(MESSAGE, Matchers.is(INVALID_USER));
    }
}
