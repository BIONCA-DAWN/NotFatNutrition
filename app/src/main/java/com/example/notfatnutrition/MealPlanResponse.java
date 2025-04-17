package com.example.notfatnutrition;

import java.util.ArrayList;

public class MealPlanResponse {
    private ArrayList<Meal> meals;

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }
}
