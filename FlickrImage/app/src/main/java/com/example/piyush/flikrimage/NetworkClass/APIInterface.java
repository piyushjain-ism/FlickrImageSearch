package com.example.piyush.flikrimage.NetworkClass;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by piyushjain on 02/04/18.
 */

public interface APIInterface {

    @GET("rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&safe_search=2&per_page=20")
    Call<JsonObject> getItemList(@Query("page") int page, @Query("text") String text);
}
