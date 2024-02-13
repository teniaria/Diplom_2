import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.client.user.UserClient;
import ru.praktikum.generator.UserGenerator;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class UserChangeTest extends BaseTest {
    public static final String OLD = "old";
    public static final String NULL = "null";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String MESSAGE_YOU_SHOULD_BE_AUTHORISED = "You should be authorised";
    private final UserGenerator generator = new UserGenerator();
    private final UserClient userClient = new UserClient();
    private final String email;
    private final String password;
    private final String name;
    private final String testName;

    public UserChangeTest(String email, String password, String name, String testName) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.testName = testName;
    }

    @Parameterized.Parameters(name = "{index} : change {3}")
    public static Object[][] getParameters() {
        return new Object[][]{
                {createUniqueEmail(), OLD, OLD, EMAIL},
                {OLD, createPassword(), OLD, PASSWORD},
                {OLD, OLD, createName(), NAME},
                {createUniqueEmail(), createPassword(), createName(), "all fields"},
                {createUniqueEmail(), NULL, NULL, "only field email, other null"},
                {createUniqueEmail(), createPassword(), NULL, "fields email + password, without name"},
                {NULL, createPassword(), NULL, "only field password, other null"},
                {NULL, createPassword(), createName(), "fields password + name, without email"},
                {NULL, NULL, createName(), "only field name, other null"},
                {createUniqueEmail(), NULL, createName(), "fields email + name, without password"}
        };
    }

    private Map<String, String> createMap(String email, String password, String name) {
        Map<String, String> updateData = new HashMap<>();
        if (email.contains(OLD)) {
            updateData.put(EMAIL, user.getEmail());
        } else if (!email.contains(NULL)) {
            updateData.put(EMAIL, email);
        }
        if (password.contains(OLD)) {
            updateData.put(PASSWORD, user.getPassword());
        } else if (!password.contains(NULL)) {
            updateData.put(PASSWORD, password);
        }
        if (name.contains(OLD)) {
            updateData.put(NAME, user.getName());
        } else if (!name.contains(NULL)) {
            updateData.put(NAME, name);
        }

        return updateData;
    }

    private static String createUniqueEmail() {
        return RandomStringUtils.randomAlphanumeric(10) + "@ww.ww";
    }

    private static String createPassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private static String createName() {
        return RandomStringUtils.randomAlphanumeric(7) ;
    }

    @Test
    @DisplayName("Изменение данных авторизованного пользователя")
    @Description("Проверка состояния кода и значения для поля success")
    public void userChangeCorrectLogin() {
        Map<String, String> updateData = createMap(email, password, name);

        Response response = userClient.changeInfoUser(updateData, accessToken);
        response.then().log().all().assertThat().statusCode(HttpStatus.SC_OK)
                .and().body(ANSEW, equalTo(true));
    }

    @Test
    @DisplayName("Изменение данных неавторизованного пользователя")
    @Description("Проверка состояния кода и значения полей: success и message")
    public void userChangeWithoutLogin() {
        Map<String, String> updateData = createMap(email, password, name);

        Response response = userClient.changeInfoUserWithoutLogin(updateData);
        response.then().log().all().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().body(ANSEW, CoreMatchers.equalTo(false))
                .and().body(MESSAGE, CoreMatchers.equalTo(MESSAGE_YOU_SHOULD_BE_AUTHORISED));
    }
}
