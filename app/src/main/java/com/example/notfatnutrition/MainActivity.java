package com.example.notfatnutrition;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "b53be265dbc1494da08d5333ba125281";

    private EditText dietInput, calorieInput;
    private Button searchButton;
    private RecyclerView recyclerView;
    private MealAdapter mealAdapter;

    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dietInput = findViewById(R.id.dietInput);
        calorieInput = findViewById(R.id.calorieInput);
        searchButton = findViewById(R.id.searchButton);
        resultText = findViewById(R.id.textView);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String diet = dietInput.getText().toString().trim();
                String caloriesStr = calorieInput.getText().toString().trim();

                if (caloriesStr.isEmpty()) {
                    resultText.setText("Please enter calories.");
                    return;
                }

                int calories = Integer.parseInt(caloriesStr);
                fetchMealPlan(diet, calories);
            }
        });
    }

    private void fetchMealPlan(String diet, int calories) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MealPlanResponse> call = apiService.getMealPlan("day", calories, diet, API_KEY);

        Log.d("API_URL", call.request().url().toString());

        call.enqueue(new Callback<MealPlanResponse>() {
            @Override
            public void onResponse(Call<MealPlanResponse> call, Response<MealPlanResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getMeals() != null) {
                    StringBuilder mealInfo = new StringBuilder();
                    recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    mealAdapter = new MealAdapter(MainActivity.this, response.body().getMeals());
                    recyclerView.setAdapter(mealAdapter);
                    resultText.setVisibility(View.GONE);

                } else {
                    resultText.setText("No meals found.");
                    Log.e("API Response", "Empty or failed response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MealPlanResponse> call, Throwable t) {
                resultText.setText("Network Error: " + t.getMessage());
                Log.e("API Error", t.getMessage(), t);
            }
        });
    }
}
