package com.noir.vknwo.network;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

public class RetrofitSpiceService extends RetrofitGsonSpiceService {

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(IVkApi.class);
    }

    @Override
    protected String getServerUrl() {
        return "https://api.vk.com/method";
    }
}
