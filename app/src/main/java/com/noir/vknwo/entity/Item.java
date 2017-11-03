package com.noir.vknwo.entity;


import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private Integer likes;
    private String text;
    private String srcSmall;
    private String srcBig;
    private String type;
    private long date;

    public Item() {

    }

    public Item(Integer likes, String text, String srcSmall, String srcBig, String type, long date) {
        this.likes = likes;
        this.text = text;
        this.srcSmall = srcSmall;
        this.srcBig = srcBig;
        this.type = type;
        this.date = date;
    }

    public static final Parcelable.Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            Item item = new Item(source.readInt(),
                    source.readString(),
                    source.readString(),
                    source.readString(),
                    source.readString(),
                    source.readLong());
            return item;
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(likes);
        dest.writeString(text);
        dest.writeString(srcSmall);
        dest.writeString(srcBig);
        dest.writeString(type);
        dest.writeLong(date);
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSrcSmall() {
        return srcSmall;
    }

    public void setSrcSmall(String srcSmall) {
        this.srcSmall = srcSmall;
    }

    public String getSrcBig() {
        return srcBig;
    }

    public void setSrcBig(String srcBig) {
        this.srcBig = srcBig;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
