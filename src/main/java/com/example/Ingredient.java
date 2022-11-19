package com.example;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {

    private List<String> ingredients = new ArrayList<>();

    public Ingredient(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public Ingredient() {
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredient) {
        this.ingredients.add(ingredient);
    }
}
