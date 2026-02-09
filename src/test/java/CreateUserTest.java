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

@Feature("Проверка регистрации пользователя")
public class CreateUserTest extends BaseTest {
    private CreateUserData createUserData;
    private String email;
    private String password;
    private String name;
    private ValidatableResponse registrationResponse;
    private ValidatableResponse authResponse;
    private String tokenAfterRegistration;
    private String tokenAfterLogin;
    private Steps steps;


    @Before
    public void setUp() {
        email = RandomStringUtils.randomAlphabetic(13) + "@yandex.ru";
        password = RandomStringUtils.randomAlphabetic(13);
        name = RandomStringUtils.randomAlphabetic(13);
    }

    @Test
    @DisplayName("Регистрация пользователя")
    public void shouldCreateUser() {
        createUserData = new CreateUserData(email, password, name);
        steps = new Steps();
        registrationResponse = steps.createUser(createUserData)
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @After
    public void tearDown() {
        steps = new Steps();

        tokenAfterRegistration = registrationResponse
                .extract()
                .path("accessToken");

        authResponse = steps.userLogin(createUserData, tokenAfterRegistration);

        tokenAfterLogin = authResponse
                .extract()
                .path("accessToken");

        steps.deleteUser(createUserData, tokenAfterLogin);

    }
}
