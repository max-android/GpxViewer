package com.primerexample.gpxviewer.ui;

import com.primerexample.gpxviewer.entities.Data;

import java.util.List;

/**
 * Created by Максим on 02.12.2017.
 */

public class DataHolder {


    private static DataHolder dataHolder;
    private List<Data> dataList;

    public void setDataList(List<Data>dataList) {
        this.dataList = dataList;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public  static DataHolder getInstance() {
        if (dataHolder == null) {
            dataHolder = new DataHolder();
        }
        return dataHolder;
    }

}
