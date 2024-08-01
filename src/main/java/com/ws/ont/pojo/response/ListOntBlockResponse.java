package com.ws.ont.pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ws.ont.pojo.OntBlock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ListOntBlockResponse {

    @ApiModelProperty(value = "Total number of items", example = "10")
    private Integer totalItems;

    @ApiModelProperty(value = "List of ONT blocks")
    private List<OntBlock> ontBlockList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "Message indicating the result of the filter operation", example = "Block for the filter not found")
    private String message;


    public ListOntBlockResponse(List<Map<String, Object>> resultTuples) {
        this.ontBlockList = new ArrayList<>();

        if (resultTuples != null) {
            resultTuples.forEach(block -> this.ontBlockList.add(new OntBlock(block)));
        }
        this.totalItems = this.ontBlockList.size();
        if (this.totalItems == 0) {
            this.message = "Block for the filter not found";
        }
    }

}
