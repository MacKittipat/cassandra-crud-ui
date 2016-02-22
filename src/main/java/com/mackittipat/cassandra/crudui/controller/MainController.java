package com.mackittipat.cassandra.crudui.controller;

import com.datastax.driver.core.*;
import com.mackittipat.cassandra.crudui.model.RowModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @RequestMapping(value = {"/", "/index"})
    public String index(Model model, @RequestParam String keySpace, @RequestParam String columnFamily) {

        Cluster cluster;
        Session session;

        cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        session = cluster.connect(keySpace);

        Map<String, String> primaryKeyMap = new HashMap<>();
        // Find primary key
        ResultSet cResultSet = session.execute("SELECT column_name, type " +
                "FROM system.schema_columns " +
                "WHERE keyspace_name='" + keySpace + "' " +
                "AND columnfamily_name='" + columnFamily + "'");
        cResultSet.all().stream()
                .filter(row -> "partition_key".equals(row.getString("type")) ||
                        "clustering_key".equals(row.getString("type")))
                .forEach(row -> {
                    primaryKeyMap.put(row.getString("column_name"), row.getString("type"));
                });

        List<RowModel> rowModelList = new ArrayList<>();

        cResultSet = session.execute("SELECT * FROM " + columnFamily);
        List<Row> cRowList = cResultSet.all();

        // Find row of column name
        RowModel colNameRowModel = new RowModel();
        colNameRowModel.setColumnList(cRowList.get(0).getColumnDefinitions().asList().stream()
                .map(ColumnDefinitions.Definition::getName)
                .collect(Collectors.toList()));
        rowModelList.add(colNameRowModel);

        // Find other rows
        cRowList.forEach(cRow -> {
            List<String> whereClauseList = new ArrayList<>();
            List<Object> columnList = new ArrayList<>();
            colNameRowModel.getColumnList().forEach(colName -> {
                String columnValue = cRow.getObject(colName.toString()).toString();
                columnList.add(columnValue);
                if(primaryKeyMap.containsKey(colName)) {
                    whereClauseList.add(colName + "=" + columnValue);
                }
            });

            RowModel rowModel = new RowModel();
            rowModel.setColumnList(columnList);
            rowModel.setWhereClause(String.join(" AND ", whereClauseList));
            rowModelList.add(rowModel);
        });

        model.addAttribute("rowModelList" , rowModelList);

        return "index";
    }
}
