package com.example.myapplication.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Model {

    private static final Model _instance = new Model();
    List<Dish> dishesList;

    public static Model instance(){
        return _instance;
    }

    private Model() {
        dishesList = new ArrayList<>();
        for(int i =0 ; i<10; i++) {
            dishesList.add(new Dish(""+i, "", "daniella daniella daniella daniella daniella daniella daniella daniella daniella daniella daniella daniella", i, ""+i));
        }

    }

    public List<Dish> getAllDishes(){
        return dishesList;
    }

    public List<Dish> getDishes(){
        String dishes="";
        try {
            dishes = DB.getAllDishes().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Log.d("TAG", ""+ParseJson.parseDishesFromJson(dishes));
        return ParseJson.parseDishesFromJson(dishes);
    }

    public void addNewDish(Dish dish){
        try {
            String response = DB.addDish(dish).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
