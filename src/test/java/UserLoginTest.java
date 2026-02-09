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
public class UserLoginTest extends BaseTest {
    private CreateUserData createUserData;
    private String email;
    private String password;
    private String name;
    private ValidatableResponse authResponse;
    private String tokenAfterRegistration;
    private String tokenAfterAuth;
    private Steps steps;


    @Before
    public void setUp() {
        email = RandomStringUtils.randomAlphabetic(13) + "@yandex.ru";
        password = RandomStringUtils.randomAlphabetic(13);
        name = RandomStringUtils.randomAlphabetic(13);
    }

    @Test
    @DisplayName("Пользователь может авторизоваться")
    public void shouldLoginUser() {
        createUserData = new CreateUserData(email, password, name);
        steps = new Steps();
        tokenAfterRegistration = steps.createUser(createUserData)
                .extract()
                .path("accessToken");
        steps.userLogin(createUserData, tokenAfterRegistration)
                .statusCode(200)
                .body("success", equalTo(true));


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
