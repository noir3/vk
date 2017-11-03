package com.noir.vknwo.network.request;

import android.util.Log;

import com.noir.vknwo.network.IVkApi;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import java.util.HashMap;
import java.util.Map;

import retrofit.client.Response;

public class NewsfeedGetRequest extends RetrofitSpiceRequest<Response, IVkApi> {

    private Map<String, String> params = new HashMap<>();

    public NewsfeedGetRequest(Map<String, String> params) {
        super(Response.class, IVkApi.class);
        this.params = params;
        Log.d("GET_NEWSFEED", params.toString());
    }

    @Override
    public Response loadDataFromNetwork() throws Exception {
        return getService().newsfeedGet(params);
    }
}
