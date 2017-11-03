package com.noir.vknwo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.noir.vknwo.network.RetrofitSpiceService;
import com.octo.android.robospice.SpiceManager;

public abstract class BaseSpiceActivity extends AppCompatActivity {

    private SpiceManager spiceManager = new SpiceManager(RetrofitSpiceService.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spiceManager.start(this);
    }

    @Override
    protected void onDestroy() {
        spiceManager.shouldStop();
        super.onDestroy();
    }

    public SpiceManager getSpiceManager() {
        return spiceManager;
    }

}
