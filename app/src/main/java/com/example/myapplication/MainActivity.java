package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.model.Dish;
import com.example.myapplication.model.Model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    RecyclerView dishesList;
    DishesRecyclerAdapter adapter;
    List<Dish> data;
    Button addDishBtn, startersBtn, pastaBtn, pizzaBtn, fishBtn, dessertsBtn;
    ExecutorService es;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addDishBtn = findViewById(R.id.main_add_btn);
        startersBtn = findViewById(R.id.main_startersBtn);
        pastaBtn = findViewById(R.id.main_pastaBtn);
        pizzaBtn = findViewById(R.id.main_pizzaBtn);
        fishBtn = findViewById(R.id.main_fishBtn);
        dessertsBtn = findViewById(R.id.main_dessertsBtn);

        data = new ArrayList<>();
        es = Executors.newSingleThreadExecutor();

        initRecyclerview();

        es.execute(() -> {
            List<Dish> arr = Model.instance().getDishes();
            data.addAll(arr);
            runOnUiThread(() -> {
            adapter.notifyDataSetChanged();
            });
        });



        adapter.setOnClickListener((int pos)-> {
            Log.d("TAG", "Row was clicked " + pos);

            Intent intent = new Intent(this, DishDetailActivity.class);
            intent.putExtra("key",pos);

            startActivity(intent);
        });

        addDishBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, AddDishActivity.class));
        });
        startersBtn.setOnClickListener(v -> {
            List<Dish> arr = data.stream().filter(d -> d.getCategory().equals("Starters")).collect(Collectors.toList());
            data.addAll(arr);
            adapter.notifyDataSetChanged();
        });
    }

    private void initRecyclerview() {
        dishesList = findViewById(R.id.main_list);

        dishesList.setHasFixedSize(true);
        dishesList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DishesRecyclerAdapter();
        dishesList.setAdapter(adapter);
    }

    //----------------------view holder--------------------------------
    class  DishesViewHolder extends RecyclerView.ViewHolder{
        TextView name, price;
        ImageView image;

        public DishesViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.row_name);
            price = itemView.findViewById(R.id.row_price);
            image = itemView.findViewById(R.id.row_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(pos);

                    Log.d("TAG", "Row was clicked " + pos);
                }
            });
        }

        public void bind(Dish dish, int pos) {
            name.setText(dish.getName());
            price.setText("₪"+dish.getPrice());

            Picasso.get()
                    .load(dish.getImage())
                    .into(image);
        }
    }
    //----------------------on item click listener--------------------------------
    public interface OnItemClickListener{
        void onItemClick(int pos);
    }
    //----------------------recycler adapter--------------------------------
    class DishesRecyclerAdapter extends RecyclerView.Adapter<DishesViewHolder>{
        OnItemClickListener listener;

        void setOnClickListener(OnItemClickListener l){
            this.listener = l;
        }

        @NonNull
        @Override
        public DishesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.dishes_list_row, parent, false);
            return new DishesViewHolder(view, listener);
        }

        @Override
        public void onBindViewHolder(@NonNull DishesViewHolder holder, int position) {
            Dish dish = data.get(position);
            holder.bind(dish, position);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}