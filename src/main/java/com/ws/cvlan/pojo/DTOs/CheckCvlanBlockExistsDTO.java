package com.ws.cvlan.pojo.DTOs;

import lombok.Data;

@Data
public class CheckCvlanBlockExistsDTO {

    private Integer svlan;
    private Integer cvlan;


    public CheckCvlanBlockExistsDTO(Integer svlan, Integer cvlan) {
        this.svlan = svlan;
        this.cvlan = cvlan;
    }
}
