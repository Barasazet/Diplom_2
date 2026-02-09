package ru.praktikum_services.stellar_burgers.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.praktikum_services.stellar_burgers.test_data_constructors.CreateOrderData;
import ru.praktikum_services.stellar_burgers.test_data_constructors.CreateUserData;

import static io.restassured.RestAssured.given;

public class Steps {
    String id;
    String path;
    int arrayId;

    @Step("Регистрация пользователя")
    public ValidatableResponse createUser(CreateUserData createUserData) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(createUserData)
                .when()
                .post("/api/auth/register")
                .then();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse userLogin(CreateUserData createUserData, String token) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .and()
                .body(createUserData)
                .when()
                .post("/api/auth/login")
                .then();
    }

    @Step("Удаление пользователя")
    public void deleteUser(CreateUserData createUserData, String token) {
        given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .and()
                .body(createUserData)
                .when()
                .delete("/api/auth/user");

    }

    @Step("Получение хэша случайного ингредиента")
    public String getRandomIngredientHash() {
        arrayId = (int) (Math.random() * 11);
        path = String.format("data[%s]._id", arrayId);
        id = given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/ingredients")
                .then()
                .extract()
                .path(path);
        return id;

    }

    @Step("Создание заказа без токена")
    public ValidatableResponse createOrderWithoutToken(CreateOrderData createOrderData) {
        return given()
                .header("Content-type", "application/json")
                .body(createOrderData)
                .when()
                .post("/api/orders")
                .then();
    }

    @Step("Создание заказа с токеном")
    public ValidatableResponse createOrderWithToken(CreateOrderData createOrderData, String token) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(createOrderData)
                .when()
                .post("/api/orders")
                .then();
    }
}

