package com.example.notfatnutrition;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private Context context;
    private List<Meal> mealList;

    public MealAdapter(Context context, List<Meal> mealList) {
        this.context = context;
        this.mealList = mealList;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = mealList.get(position);
        holder.mealTitle.setText(meal.getTitle());

        Glide.with(context)
                .load("https://spoonacular.com/recipeImages/" + meal.getId() + "-312x231.jpg")
                .into(holder.mealImage);


        holder.itemView.setOnClickListener(v -> {
            String url = "https://spoonacular.com/recipes/" + meal.getTitle().replace(" ", "-") + "-" + meal.getId();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView mealTitle;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.mealImage);
            mealTitle = itemView.findViewById(R.id.mealTitle);
        }
    }
}
