package com.noir.vknwo.events;


import com.noir.vknwo.entity.Item;

import java.util.List;

public class NetworkNewsfeedGetEvent {
    private int status;
    private List<Item> items;
    private boolean first;
    private String nextPage;

    public NetworkNewsfeedGetEvent(int status, List<Item> items, boolean first, String nextPage) {
        this.status = status;
        this.items = items;
        this.first = first;
        this.nextPage = nextPage;
    }

    public int getStatus() {
        return status;
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean isFirst() {
        return first;
    }

    public String getNextPage() {
        return nextPage;
    }
}
