package com.m.githubs.api;

import com.m.githubs.model.ItemResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by kadan on 2/1/18.
 */

public interface Service {
    public static final String TOKEN = "access_token=cc4eae6c3054a503473ba8b8627de6198e05c890";

//    @GET("/search/users?q=language:java+location:nairobi&per_page=100")
//    Call<ItemResponse> getItems();

//    @GET("/search/users")
    @GET
    Call<ItemResponse> getItems(@Url String url);

    @GET("/users/mulukadan" +TOKEN)
    Call<ItemResponse> getUser();
}
