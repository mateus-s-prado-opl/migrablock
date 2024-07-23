package com.ws.ont.pojo.DTOs;

import com.ws.ont.enums.PortDetailsAttr;
import lombok.Data;

import java.util.Map;

import static com.ws.cvlan.util.StringUtilSol.getLong;

@Data
public class PortDetailsDto {

    private Long oltPtpId;
    private Long oltCtpId;

    public PortDetailsDto(Map<String, Object> result) {
        this.oltPtpId = getLong(result, PortDetailsAttr.OLT_PTP_ID);
        this.oltCtpId = getLong(result, PortDetailsAttr.OLT_CTP_ID);
    }
}
