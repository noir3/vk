package com.noir.vknwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.noir.vknwo.entity.Item;
import com.noir.vknwo.events.NetworkNewsfeedGetEvent;
import com.noir.vknwo.network.listeners.NewsfeedGetListener;
import com.noir.vknwo.network.request.NewsfeedGetRequest;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestActivity extends BaseSpiceActivity implements SwipeRefreshLayout.OnRefreshListener, INewsAdapter {

    List<Item> items = new ArrayList<>();
    RecyclerView recyclerView;
    NewsAdapter adapter;
    int count = 5;
    String nextpage;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        EventBus.getDefault().register(this);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_test);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (savedInstanceState == null) {
            getNewsfeed(true, "");
        } else {
            items = savedInstanceState.getParcelableArrayList("items");
            adapter = new NewsAdapter(recyclerView, TestActivity.this, items, TestActivity.this);
            recyclerView.setAdapter(adapter);
            nextpage = savedInstanceState.getString("nextpage");
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (items.size() > 0 && nextpage != null) {
            outState.putParcelableArrayList("items", new ArrayList<Item>(items));
            outState.putString("nextpage", nextpage);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onEvent(final NetworkNewsfeedGetEvent networkNewsfeedGetEvent) {
        switch (networkNewsfeedGetEvent.getStatus()) {
            case 0:
                if (networkNewsfeedGetEvent.isFirst()) {
                    adapter = new NewsAdapter(recyclerView, TestActivity.this, networkNewsfeedGetEvent.getItems(), TestActivity.this);
                    recyclerView.setAdapter(adapter);
                    items = networkNewsfeedGetEvent.getItems();
                    nextPage(networkNewsfeedGetEvent.getNextPage());
                    nextpage = networkNewsfeedGetEvent.getNextPage();
                } else {

                    nextPage(networkNewsfeedGetEvent.getNextPage());
                    nextpage = networkNewsfeedGetEvent.getNextPage();
                    items.remove(items.size() - 1);
                    adapter.notifyItemRemoved(items.size());
                    items.addAll(networkNewsfeedGetEvent.getItems());
                    adapter.notifyDataSetChanged();
                    adapter.setLoaded();
                }
                break;
            default:
                if (!networkNewsfeedGetEvent.isFirst()) {
                    items.remove(items.size() - 1);
                    adapter.notifyItemRemoved(items.size());
                    adapter.notifyDataSetChanged();
                    adapter.setLoaded();
                }
        }

        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    private void nextPage(final String next) {
        adapter.setLoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                items.add(null);
                adapter.notifyItemInserted(items.size() - 1);
                getNewsfeed(false, next);
            }
        });
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        getNewsfeed(true, "");
    }

    private void getNewsfeed(boolean type, String next) {

        Map<String, String> params = new HashMap<>();
        params.put("user_id", VKSdk.getAccessToken().userId);
        params.put("filters", "post");
        params.put("count", String.valueOf(count));
        if (!type) params.put("start_from", next);
        params.put("access_token", VKAccessToken.currentToken().accessToken);
        params.put("v", "5.13");
        getSpiceManager().execute(new NewsfeedGetRequest(params), new NewsfeedGetListener(type));

    }

    @Override
    public void onClickItem(int position) {
        try {
            if (items.size() > 0) {
                Intent intent = new Intent(getApplicationContext(), TestItemActivity.class);
                intent.putExtra("text", items.get(position).getText());
                intent.putExtra("likes", items.get(position).getLikes());
                startActivity(intent);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
