package com.mackittipat.cassandra.crudui.model;

import java.util.List;

public class RowModel {

    private List<Object> columnList;

    private String whereClause;

    public List<Object> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Object> columnList) {
        this.columnList = columnList;
    }

    public String getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }
}
