package com.ws.cvlan.pojo.response;

import com.ws.cvlan.pojo.CvlanBlock;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ListCvlanBlockResponse {

    private Integer totalItems;
    private List<CvlanBlock> cvlanBlockList;

    public ListCvlanBlockResponse(List<Map<String, Object>> resultTuples) {
        this.cvlanBlockList = new ArrayList<>();
        resultTuples.forEach(block -> this.cvlanBlockList.add(new CvlanBlock(block)));
        this.totalItems = this.cvlanBlockList.size();
    }
}
