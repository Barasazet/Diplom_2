import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import ru.stellarburgers.steps.Steps;
import ru.stellarburgers.constructors.CreateOrderData;

import java.util.Arrays;
import java.util.List;

@Feature("Проверка создания заказа")
public class OrderWithWrongHashIngredientTest extends BaseTest {
    private CreateOrderData createOrderData;
    private String firstIngredientId;
    private String secondIngredientId;
    private List<String> ingredients;
    private Steps steps;


    @Test
    @DisplayName("Невозможно создать заказ с невалидным хэшем ингредиента")
    @Description("EПри создании бургера необходимо указывать корректный хэш ингредиентов")
    public void cantCreateOrderWithWrongHashIngredient() {
        steps = new Steps();
        firstIngredientId = RandomStringUtils.randomAlphabetic(13);
        secondIngredientId = RandomStringUtils.randomAlphabetic(13);
        ingredients = Arrays.asList(firstIngredientId, secondIngredientId);
        createOrderData = new CreateOrderData(ingredients);
        steps.createOrderWithoutToken(createOrderData)
                .statusCode(500);
    }
}

