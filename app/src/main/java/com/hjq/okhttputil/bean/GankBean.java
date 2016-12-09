package com.hjq.okhttputil.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by hjq on 16-12-2.
 */

public class GankBean implements Parcelable {
    public boolean error;
    public List<ResultsBean> results;

    public static class ResultsBean implements Parcelable {
        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;
        public List<String> images;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this._id);
            dest.writeString(this.createdAt);
            dest.writeString(this.desc);
            dest.writeString(this.publishedAt);
            dest.writeString(this.source);
            dest.writeString(this.type);
            dest.writeString(this.url);
            dest.writeByte(this.used ? (byte) 1 : (byte) 0);
            dest.writeString(this.who);
            dest.writeStringList(this.images);
        }

        public ResultsBean() {
        }

        protected ResultsBean(Parcel in) {
            this._id = in.readString();
            this.createdAt = in.readString();
            this.desc = in.readString();
            this.publishedAt = in.readString();
            this.source = in.readString();
            this.type = in.readString();
            this.url = in.readString();
            this.used = in.readByte() != 0;
            this.who = in.readString();
            this.images = in.createStringArrayList();
        }

        public static final Parcelable.Creator<ResultsBean> CREATOR = new Parcelable.Creator<ResultsBean>() {
            @Override
            public ResultsBean createFromParcel(Parcel source) {
                return new ResultsBean(source);
            }

            @Override
            public ResultsBean[] newArray(int size) {
                return new ResultsBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.error ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.results);
    }

    public GankBean() {
    }

    protected GankBean(Parcel in) {
        this.error = in.readByte() != 0;
        this.results = in.createTypedArrayList(ResultsBean.CREATOR);
    }

    public static final Parcelable.Creator<GankBean> CREATOR = new Parcelable.Creator<GankBean>() {
        @Override
        public GankBean createFromParcel(Parcel source) {
            return new GankBean(source);
        }

        @Override
        public GankBean[] newArray(int size) {
            return new GankBean[size];
        }
    };
}
