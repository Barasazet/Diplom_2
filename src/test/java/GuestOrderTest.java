import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.stellarburgers.steps.Steps;
import ru.stellarburgers.constructors.CreateOrderData;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

@Feature("Проверка создания заказа")
public class GuestOrderTest extends BaseTest {
    private CreateOrderData createOrderData;
    private String firstIngredientId;
    private String secondIngredientId;
    private List<String> ingredients;
    private Steps steps;

    @Before
    public void Setup() {
        steps = new Steps();
        firstIngredientId = steps.getRandomIngredientHash();
        secondIngredientId = steps.getRandomIngredientHash();
    }

    @Test
    @DisplayName("Гость может создать заказ")
    @Description("Неавторизованный пользователь может создать свой бургер")
    public void guestCanCreateOrder() {
        steps = new Steps();

        ingredients = Arrays.asList(firstIngredientId, secondIngredientId);
        createOrderData = new CreateOrderData(ingredients);


        steps.createOrderWithoutToken(createOrderData)
                .statusCode(200)
                .body("success", equalTo(true));

    }

    @Test
    @DisplayName("Гость не может создать пустой заказ")
    @Description("Неавторизованный пользователь не может создать бургер без ингредиентов")
    public void guestCannotCreateEmptyOrder() {
        steps = new Steps();
        firstIngredientId = null;
        secondIngredientId = null;
        List<String> ingredients = Arrays.asList(firstIngredientId, secondIngredientId);
        createOrderData = new CreateOrderData(ingredients);
        steps.createOrderWithoutToken(createOrderData)
                .statusCode(400)
                .body("success", equalTo(false));
    }
}
