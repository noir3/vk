package com.noir.vknwo.network.listeners;

import android.util.Log;

import com.noir.vknwo.entity.Item;
import com.noir.vknwo.events.NetworkNewsfeedGetEvent;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class NewsfeedGetListener implements RequestListener<Response> {

    boolean first;
    String nextPage;

    public NewsfeedGetListener(boolean first) {
        this.first = first;
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        EventBus.getDefault().post(new NetworkNewsfeedGetEvent(2, null, false, null));
    }

    @Override
    public void onRequestSuccess(Response response) {
        try {
            JSONObject jsonObject = new JSONObject(new String(((TypedByteArray)
                    response.getBody()).getBytes()));

            //Log.d("GET_NEWSFEED", jsonObject.toString());

            JSONArray jsonArrayItems = jsonObject.getJSONObject("response").getJSONArray("items");
            List<Item> items = new ArrayList<>();
            for (int i = 0; i < jsonArrayItems.length(); i++) {
                try {
                    JSONObject jsonItem = jsonArrayItems.getJSONObject(i);
                    Item item = new Item();

                    item.setLikes(jsonItem.getJSONObject("likes").getInt("count"));
                    item.setText(jsonItem.getString("text"));
                    item.setDate(jsonItem.getInt("date"));

                    try {
                        if (jsonItem.getJSONArray("attachments").length() > 0) {
                            JSONObject jsonPhoto = jsonItem.getJSONArray("attachments").getJSONObject(0);
                            item.setType(jsonPhoto.getString("type"));
                            switch (jsonPhoto.getString("type")) {
                                case "photo":
                                    item.setSrcBig(jsonPhoto.getJSONObject("photo").getString("photo_604"));
                                    item.setSrcSmall(jsonPhoto.getJSONObject("photo").getString("photo_75"));
                                    break;
                                case "link":
                                    //item.setSrcBig(jsonPhoto.getJSONObject("link").getString("image_big"));
                                    //item.setSrcSmall(jsonPhoto.getJSONObject("link").getString("image_src"));
                                    break;

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    items.add(item);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (items.size() > 0) {
                EventBus.getDefault().post(new NetworkNewsfeedGetEvent(0, items, first, jsonObject.getJSONObject("response").getString("next_from")));
            } else {
                EventBus.getDefault().post(new NetworkNewsfeedGetEvent(1, null, false, null));
            }
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
