import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.stellar_burgers.steps.Steps;
import ru.praktikum_services.stellar_burgers.test_data_constructors.CreateUserData;

import static org.hamcrest.CoreMatchers.equalTo;

@Feature("Проверка авторизации пользователя")
public class UserLoginWithWrongDataTest extends BaseTest {
    private CreateUserData createUserData;
    private String email;
    private String password;
    private String name;
    private ValidatableResponse authResponse;
    private String tokenAfterRegistration;
    private String tokenAfterAuth;
    private String savedEmail;
    private String savedPassword;
    private Steps steps;


    @Before
    public void setUp() {
        email = RandomStringUtils.randomAlphabetic(13) + "@yandex.ru";
        password = RandomStringUtils.randomAlphabetic(13);
        name = RandomStringUtils.randomAlphabetic(13);
    }

    @Test
    @DisplayName("Невозможно авторизоваться с неправильным email")
    public void cantLoginWithWrongEmail() {
        createUserData = new CreateUserData(email, password, name);
        steps = new Steps();

        tokenAfterRegistration = steps.createUser(createUserData)
                .extract()
                .path("accessToken");
        savedEmail = createUserData.email;
        createUserData.email = RandomStringUtils.randomAlphabetic(13) + "@yandex.ru";
        steps.userLogin(createUserData, tokenAfterRegistration)
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));

        createUserData.email = savedEmail;
    }

    @Test
    @DisplayName("Невозможно авторизоваться с неправильным паролем")
    public void cantLoginWithWrongPassword() {
        createUserData = new CreateUserData(email, password, name);
        steps = new Steps();

        tokenAfterRegistration = steps.createUser(createUserData)
                .extract()
                .path("accessToken");
        savedPassword = createUserData.password;
        createUserData.password = RandomStringUtils.randomAlphabetic(13);
        steps.userLogin(createUserData, tokenAfterRegistration)
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));

        createUserData.password = savedPassword;
    }

    @After
    public void tearDown() {
        steps = new Steps();

        authResponse = steps.userLogin(createUserData, tokenAfterRegistration);
        tokenAfterAuth = authResponse
                .extract()
                .path("accessToken");

        steps.deleteUser(createUserData, tokenAfterAuth);

    }
}
