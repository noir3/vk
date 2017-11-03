package com.noir.vknwo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TestItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_item);

        ((TextView) findViewById(R.id.tv_text)).setText(getIntent().getStringExtra("text"));
        ((TextView) findViewById(R.id.tv_likes)).setText(String.valueOf(getIntent().getIntExtra("likes", 0)));

    }
}
