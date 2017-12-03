package com.primerexample.gpxviewer.entities;

import android.os.Parcel;
import android.os.Parcelable;


public class Data implements Parcelable {


    private double latitude;
    private double longitude;

    public Data(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Data(Parcel parcel){

       double[] data=new double[2];
        parcel.readDoubleArray(data);
        latitude = data[0];
        longitude= data[1];

    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {


        parcel.writeDoubleArray(new double[]{latitude, longitude});

    }

    public static final Creator<Data> CREATOR =new Creator<Data>(){


        @Override
        public Data createFromParcel(Parcel parcel) {
            return new Data(parcel);
        }

        @Override
        public Data[] newArray(int i) {
            return new Data[i];
        }
    };



}
