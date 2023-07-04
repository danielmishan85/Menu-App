package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.model.Dish;
import com.example.myapplication.model.Model;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DishDetailActivity extends AppCompatActivity {

    int position;
    Dish dish;
    TextView name, price, description;
    ImageView image;
    ImageButton backBtn;
    ExecutorService es;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_detail);

        position = (int) getIntent().getSerializableExtra("key");
        es = Executors.newSingleThreadExecutor();


        dish = Model.instance().getDishes().get(position);


        name = findViewById(R.id.detail_name);
        price = findViewById(R.id.detail_price);
        description = findViewById(R.id.detail_decsription);
        image = findViewById(R.id.detail_img);
        backBtn = findViewById(R.id.detail_backBtn);

        setDishDetails();

        backBtn.setOnClickListener(v -> finish());
    }

    private void setDishDetails() {
        name.setText(this.dish.getName());
        price.setText("₪"+this.dish.getPrice());
        description.setText(this.dish.getDescription());

        Picasso.get()
                .load(this.dish.getImage())
                .into(image);
    }
}