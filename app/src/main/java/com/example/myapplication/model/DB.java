package com.example.myapplication.model;


import android.util.Log;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DB {

    //GET - get all dishes list
    public static CompletableFuture<String> getAllDishes(){
        String url = "http://10.0.2.2:3001/dish/getAllDishes";
        //String url = "https://localhost:3001/dish/getAllDishes";
        CompletableFuture<String> future = new CompletableFuture<>();
        getRequest(url, new RequestCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d("server connection", "get dishes success with response: " + response);

                future.complete(response);

            }

            @Override
            public void onFailure(String error) {
                future.completeExceptionally(new Exception(error));
            }
        });

        return future;
    }
    interface RequestCallback {
        void onSuccess(String response);
        void onFailure(String error);
    }

    static void getRequest(String postUrl, RequestCallback callback) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e.getMessage());
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                callback.onSuccess(responseBody);
                Log.d("server", "getRequest" + responseBody);
            }
        });
    }

    //POST - and new dish
    public static CompletableFuture<String> addDish(Dish dish) {
        String postUrl = "http://10.0.2.2:3001/dish/addDish";

        String postBody = "{\n" +
                "\"dishName\": \"" + dish.getName() + "\",\n" +
                "\"dishCategory\": \"" + dish.getCategory() + "\",\n" +
                "\"dishDescription\": \"" + dish.getDescription() + "\",\n" +
                "\"dishImage\": \"" + dish.getImage() + "\",\n" +
                "\"dishPrice\": \"" + dish.getPrice() + "\"\n" +
                "}";

        CompletableFuture<String> future = new CompletableFuture<>();

        try {
            postRequest(postUrl, postBody, new RequestCallback() {
                @Override
                public void onSuccess(String response) {
                    future.complete(response);
                    Log.d("server connection addDish", "addDish finished with response: " + response);
                }

                @Override
                public void onFailure(String error) {
                    future.complete(error);
                    Log.d("server connection addDish", "addDish failed with response: " + error);
                    new Exception(error);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return future;
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    static void postRequest(String postUrl,String postBody,RequestCallback callback) throws IOException {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, postBody);

        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e.getMessage());
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                callback.onSuccess(responseBody);
            }
        });
    }

}


