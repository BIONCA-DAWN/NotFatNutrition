package com.example.notfatnutrition;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("mealplanner/generate")
    Call<MealPlanResponse> getMealPlan(
            @Query("timeFrame") String timeFrame,
            @Query("targetCalories") int targetCalories,
            @Query("diet") String diet,
            @Query("apiKey") String apiKey
    );
}
