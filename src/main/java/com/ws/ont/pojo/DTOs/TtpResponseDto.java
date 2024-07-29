package com.ws.ont.pojo.DTOs;

import lombok.Data;

import java.util.Map;

@Data
public class TtpResponseDto {
    private Long vTtpId;
    private Integer vOutputCode;
    private String vOutputDescription;

    public TtpResponseDto(Map<String, Object> out) {
        this.vTtpId = (Long) out.get("P_ID_ENTITY_TTP_OSS");
        this.vOutputCode = (Integer) out.get("OUTOUTPUTCODE");
        this.vOutputDescription = (String) out.get("OUTCODEDESCRIPTION");
    }

    public TtpResponseDto(Long oltTtpId) {
        this.vTtpId = oltTtpId;
    }
}
