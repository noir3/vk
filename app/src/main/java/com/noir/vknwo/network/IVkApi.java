package com.noir.vknwo.network;


import java.util.Map;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.QueryMap;

public interface IVkApi {
    @GET("/newsfeed.get")
    Response newsfeedGet(
            @QueryMap Map<String, String> params);
}
