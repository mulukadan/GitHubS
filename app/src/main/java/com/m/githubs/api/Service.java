package com.m.githubs.api;

import com.m.githubs.model.Follower;
import com.m.githubs.model.ItemResponse;
import com.m.githubs.model.Repo;
import com.m.githubs.model.User;

import java.util.List;

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

    @GET
    Call <List<Repo>> getUserRepos(@Url String url);

    @GET
    Call <List<Follower>> getUserFollowers(@Url String url);

//AM Using same object class for both Followers and following cos the structure is the same
    @GET
    Call <List<Follower>> getUserFollowing(@Url String url);
}
