package com.m.githubs.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kadan on 2/1/18.
 */

public class Client {
    public static final String BASE_URL = "http://api.github.com";

    public static Retrofit retrofit = null;

    public static  Retrofit getClient(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
