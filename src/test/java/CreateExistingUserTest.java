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
public class CreateExistingUserTest extends BaseTest {
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
    @DisplayName("Невозможно создать пользователя, который уже зарегистрирован")
    @Description("Нельзя создать пользователя с идентичным email")
    public void cantCreateAlreadyExistingUser() {
        createUserData = new CreateUserData(email, password, name);
        steps = new Steps();

        steps.createUser(createUserData);
        steps.createUser(createUserData)
                .statusCode(403)
                .body("message", equalTo("User already exists"));

    }

}