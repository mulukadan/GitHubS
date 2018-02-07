package com.m.githubs.api;

import com.m.githubs.model.ItemResponse;
import com.m.githubs.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by kadan on 2/1/18.
 */

public interface Service {
    @GET
    Call<ItemResponse> getItems(@Url String url);

    @GET
    Call <User> getUser(@Url String url);
}
