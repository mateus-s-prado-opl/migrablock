package com.ws.ont.pojo.DTOs;

import lombok.Data;

import java.util.Map;

@Data
public class CtpResponseDto {
    private Long ctpGponId;
    private Integer vOutputCode;
    private String vOutputDescription;


    public CtpResponseDto(Map<String, Object> out) {
        this.ctpGponId = (Long) out.get("P_ID_ENTITY_CTP_OSS");
        this.vOutputCode = (Integer) out.get("OUTOUTPUTCODE");         //TODO: Verificar output code 0
        this.vOutputDescription = (String) out.get("OUTCODEDESCRIPTION");
    }
}
