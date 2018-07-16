package com.example.piyush.flikrimage.NetworkClass;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityModel {

    APIInterface apiService =
            APIClient.getClient().create(APIInterface.class);

    public interface OnLoginFinishedListener {
        void onSuccess(JsonArray jsonArray);
        void onFailure(String message);
    }
    public void search(int page, String query, final OnLoginFinishedListener listener) {

        apiService.getItemList(page,query).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                JsonArray jsonArray = response.body().getAsJsonObject("photos").getAsJsonArray("photo");
                listener.onSuccess(jsonArray);

               }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }
}
