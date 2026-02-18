import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.stellarburgers.steps.Steps;
import ru.stellarburgers.constructors.CreateUserData;

import static org.hamcrest.CoreMatchers.equalTo;

@Feature("Проверка авторизации пользователя")
public class UserLoginTest extends BaseTest {
    private CreateUserData createUserData;
    private String email;
    private String password;
    private String name;
    private ValidatableResponse authResponse;
    private ValidatableResponse registerResponse;
    private String tokenAfterRegistration;
    private String tokenAfterAuth;
    private Steps steps;


    @Before
    public void setUp() {
        email = RandomStringUtils.randomAlphabetic(13) + "@yandex.ru";
        password = RandomStringUtils.randomAlphabetic(13);
        name = RandomStringUtils.randomAlphabetic(13);
        createUserData = new CreateUserData(email, password, name);
        steps = new Steps();
        registerResponse = steps.createUser(createUserData);
    }

    @Test
    @DisplayName("Пользователь может авторизоваться")
    @Description("При использовании валидных данных, пользователь успешно авторизуется")
    public void shouldLoginUser() {

        tokenAfterRegistration = registerResponse
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
