import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.stellarburgers.steps.Steps;
import ru.stellarburgers.constructors.CreateUserData;

import static org.hamcrest.CoreMatchers.equalTo;

@Feature("Проверка регистрации пользователя")
public class CreateUserWithoutFieldTest extends BaseTest {
    private Steps steps;
    private CreateUserData createUserData;
    private String email;
    private String password;
    private String name;

    @Before
    public void setUp() {
        email = RandomStringUtils.randomAlphabetic(13) + "@yandex.ru";
        password = RandomStringUtils.randomAlphabetic(13);
        name = RandomStringUtils.randomAlphabetic(13);
    }

    @Test
    @DisplayName("Невозможно зарегистрироваться без email")
    @Description("Email - одно из обязательных полей")
    public void cantCreateUserWithoutEmail() {
        createUserData = new CreateUserData(email, password, name);
        steps = new Steps();

        createUserData.email = null;
        steps.createUser(createUserData)
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Невозможно зарегистрироваться без пароля")
    @Description("Пароль - одно из обязательных полей")
    public void cantCreateUserWithoutPassword() {
        createUserData = new CreateUserData(email, password, name);
        steps = new Steps();

        createUserData.password = null;
        steps.createUser(createUserData)
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Невозможно зарегистрироваться без имени")
    @Description("Имя - одно из обязательных полей")
    public void cantCreateUserWithoutName() {
        createUserData = new CreateUserData(email, password, name);
        steps = new Steps();

        createUserData.name = null;
        steps.createUser(createUserData)
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
