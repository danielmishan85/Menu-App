package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.model.Dish;
import com.example.myapplication.model.Model;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AddDishActivity extends AppCompatActivity {

    TextInputEditText name, price, image, description;
    Button createDishBtn;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);

        name = findViewById(R.id.new_dish_name_input_ed);
        price = findViewById(R.id.new_dish_price_input_ed);
        image = findViewById(R.id.new_dish_image_input_ed);
        description = findViewById(R.id.new_dish_description_input_ed);
        createDishBtn = findViewById(R.id.new_dish_btn);
        backBtn = findViewById(R.id.new_dish_back_btn);

        createDishBtn.setOnClickListener(v -> {
            String name = this.name.getText().toString();
            int price = Integer.parseInt(this.price.getText().toString());
            String image = this.image.getText().toString();
            String description = this.description.getText().toString();
            String category = "Starters";

            // Call the addDish function with the obtained details
            Dish dish = new Dish(name, image, description, price, category);
            Log.d("TAG", dish.getName());

            Model.instance().addNewDish(dish);
            Toast.makeText(this, "ADDED DISH SUCCESSFULLY", Toast.LENGTH_SHORT).show();
            finish();
        });


        backBtn.setOnClickListener(v -> finish());
    }
}