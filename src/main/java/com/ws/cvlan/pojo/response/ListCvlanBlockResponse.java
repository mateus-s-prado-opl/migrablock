package com.ws.cvlan.pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ws.cvlan.pojo.CvlanBlock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ListCvlanBlockResponse {

    @ApiModelProperty(value = "Total number of items", example = "10")
    private Integer totalItems;

    @ApiModelProperty(value = "List of CVLAN blocks")
    private List<CvlanBlock> cvlanBlockList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "Message indicating the result of the filter operation", example = "Block for the filter not found")
    private String message;

    public ListCvlanBlockResponse(List<Map<String, Object>> resultTuples) {
        this.cvlanBlockList = new ArrayList<>();

        if (resultTuples != null) {
            resultTuples.forEach(block -> this.cvlanBlockList.add(new CvlanBlock(block)));
        }
        this.totalItems = this.cvlanBlockList.size();
        if (this.totalItems == 0) {
            this.message = "Block for the filter not found";
        }
    }
}
