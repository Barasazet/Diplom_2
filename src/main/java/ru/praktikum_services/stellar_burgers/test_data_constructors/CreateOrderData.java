package ru.praktikum_services.stellar_burgers.test_data_constructors;

import java.util.List;

public class CreateOrderData {
    public List<String> ingredients;

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public CreateOrderData(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public CreateOrderData() {
    }
}
