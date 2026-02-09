import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import org.junit.BeforeClass;

@Epic("Тестирование API StellarBurgers")
public class BaseTest {
    final static String BASE_URL = "https://stellarburgers.education-services.ru/";

    @BeforeClass
    public static void UrlSetUp() {
        RestAssured.baseURI = BASE_URL;
    }

}
