import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.stellar_burgers.steps.Steps;
import ru.praktikum_services.stellar_burgers.test_data_constructors.CreateOrderData;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

@Feature("Проверка создания заказа")
public class GuestOrderTest extends BaseTest {
    private CreateOrderData createOrderData;
    private String id1;
    private String id2;
    private List<String> ingredients;
    private Steps steps;

    @Before
    public void Setup() {
        steps = new Steps();
        id1 = steps.getRandomIngredientHash();
        id2 = steps.getRandomIngredientHash();
    }

    @Test
    @DisplayName("Гость может создать заказа")
    public void guestCanCreateOrder() {
        steps = new Steps();

        ingredients = Arrays.asList(id1, id2);
        createOrderData = new CreateOrderData(ingredients);


        steps.createOrderWithoutToken(createOrderData)
                .statusCode(200)
                .body("success", equalTo(true));

    }

    @Test
    @DisplayName("Гость не может создать пустой заказ")
    public void guestCannotCreateEmptyOrder() {
        steps = new Steps();
        id1 = null;
        id2 = null;
        List<String> ingredients = Arrays.asList(id1, id2);
        createOrderData = new CreateOrderData(ingredients);
        steps.createOrderWithoutToken(createOrderData)
                .statusCode(400)
                .body("success", equalTo(false));
    }
}
