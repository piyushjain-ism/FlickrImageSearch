package com.example.piyush.flikrimage.NetworkClass;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by piyushjain on 02/04/18.
 */

public class APIClient {
    public static final String BASE_URL = "https://api.flickr.com/services/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }



        return retrofit;
    }
}
