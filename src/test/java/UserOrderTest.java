import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.stellarburgers.steps.Steps;
import ru.stellarburgers.constructors.CreateOrderData;
import ru.stellarburgers.constructors.CreateUserData;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

@Feature("Проверка создания заказа")
public class UserOrderTest extends BaseTest {
    private String email;
    private String password;
    private String name;
    private String firstIngredientId;
    private String secondIngredientId;
    private Steps steps;
    private String tokenAfterRegistration;
    private String tokenAfterLogin;
    private ValidatableResponse registrationResponse;
    private ValidatableResponse loginResponse;
    private CreateUserData createUserData;
    private CreateOrderData createOrderData;
    private List<String> ingredients;


    @Before
    public void setUp() {
        steps = new Steps();

        email = RandomStringUtils.randomAlphabetic(13) + "@yandex.ru";
        password = RandomStringUtils.randomAlphabetic(13);
        name = RandomStringUtils.randomAlphabetic(13);
        createUserData = new CreateUserData(email, password, name);
        registrationResponse = steps.createUser(createUserData);
        tokenAfterRegistration = registrationResponse
                .extract()
                .path("accessToken");

        firstIngredientId = steps.getRandomIngredientHash();
        secondIngredientId = steps.getRandomIngredientHash();

    }

    @Test
    @DisplayName("Пользователь может создать заказ")
    @Description("Пользователь может составить рецепт бургера")
    public void userCanCreateOrder() {
        steps = new Steps();
        ingredients = Arrays.asList(firstIngredientId, secondIngredientId);
        createOrderData = new CreateOrderData(ingredients);

        loginResponse = steps.userLogin(createUserData, tokenAfterRegistration);

        tokenAfterLogin = loginResponse
                .extract()
                .path("accessToken");
        steps.createOrderWithToken(createOrderData, tokenAfterLogin)
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Пользователь не может создать пустой заказ")
    @Description("Невозможно создать бургер без ингредиентов")
    public void userCannotCreateEmptyOrder() {
        steps = new Steps();
        firstIngredientId = null;
        secondIngredientId = null;
        ingredients = Arrays.asList(firstIngredientId, secondIngredientId);
        createOrderData = new CreateOrderData(ingredients);

        loginResponse = steps.userLogin(createUserData, tokenAfterRegistration);

        tokenAfterLogin = loginResponse
                .extract()
                .path("accessToken");
        steps.createOrderWithToken(createOrderData, tokenAfterLogin)
                .statusCode(400)
                .body("success", equalTo(false));
    }

    @After
    public void tearDown() {
        steps = new Steps();
        steps.deleteUser(createUserData, tokenAfterLogin);
    }
}
