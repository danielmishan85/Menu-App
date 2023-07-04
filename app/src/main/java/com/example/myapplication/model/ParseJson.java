package com.example.myapplication.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParseJson {

    public static ArrayList<Dish> parseDishesFromJson(String json) {
        ArrayList<Dish> dishes = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++)
                dishes.add(parseDish(jsonArray.getJSONObject(i)));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    private static Dish parseDish(JSONObject jsonDish) throws JSONException {
        JSONObject jsonObject = jsonDish;
        Dish dish = new Dish();
        dish.setName(jsonObject.getString("dishName"));
        dish.setDescription(jsonObject.getString("dishDescription"));
        dish.setImage(jsonObject.getString("dishImage"));
        dish.setPrice(jsonObject.getInt("dishPrice"));
        dish.setCategory(jsonObject.getString("dishCategory"));

        return dish;
    }
}
