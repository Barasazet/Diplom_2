import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import ru.praktikum_services.stellar_burgers.steps.Steps;
import ru.praktikum_services.stellar_burgers.test_data_constructors.CreateOrderData;

import java.util.Arrays;
import java.util.List;

@Feature("Проверка создания заказа")
public class OrderWithWrongHashIngredientTest extends BaseTest {
    private CreateOrderData createOrderData;
    private String id1;
    private String id2;
    private List<String> ingredients;
    private Steps steps;


    @Test
    @DisplayName("Невозможно создать заказ с невалидным хэшем ингредиента")
    public void cantCreateOrderWithWrongHashIngredient() {
        steps = new Steps();
        id1 = RandomStringUtils.randomAlphabetic(13);
        id2 = RandomStringUtils.randomAlphabetic(13);
        ingredients = Arrays.asList(id1, id2);
        createOrderData = new CreateOrderData(ingredients);
        steps.createOrderWithoutToken(createOrderData)
                .statusCode(500);
    }
}

