package com.noir.vknwo;


import android.support.v4.app.Fragment;

import com.noir.vknwo.network.RetrofitSpiceService;
import com.octo.android.robospice.SpiceManager;

public class BaseSpaceFragment extends Fragment {

    public BaseSpaceFragment() {
    }

    private SpiceManager spiceManager = new SpiceManager(RetrofitSpiceService.class);

    @Override
    public void onStart() {
        spiceManager.start(getContext());
        super.onStart();
    }

    @Override
    public void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    protected SpiceManager getSpiceManager() {
        return spiceManager;
    }
}
