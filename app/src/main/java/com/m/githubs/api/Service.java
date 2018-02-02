package com.m.githubs.api;

import com.m.githubs.model.ItemResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by kadan on 2/1/18.
 */

public interface Service {
    @GET("/search/users?q=language:java+location:lagos")
    Call<ItemResponse> getItems();
}
