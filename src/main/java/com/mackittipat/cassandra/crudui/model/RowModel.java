package com.mackittipat.cassandra.crudui.model;

import java.util.List;

public class RowModel {

    private List<Object> columnList;

    public List<Object> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Object> columnList) {
        this.columnList = columnList;
    }
}
