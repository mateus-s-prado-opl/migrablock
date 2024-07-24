package com.ws.ont.pojo.DTOs;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ListOltDto {
    private List<OltDto> oltsList;


    public ListOltDto(List<Map<String, Object>> resultTuples) {
        this.oltsList = new ArrayList<>();
        resultTuples.forEach(block -> this.oltsList.add(new OltDto(block)));
    }
}
