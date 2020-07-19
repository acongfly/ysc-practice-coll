package com.acongfly.studyjava.http;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface CommonApi {
    @GET("users/{user}/gists")
    Single<List<String>> getGists(@Path("user") String user);

    @GET("users/{user}/repos")
    Single<List<String>> getRepos(@Path("user") String user);

    @GET("users/{user}/info")
    Call<String> get(@Path("user") String user);

}