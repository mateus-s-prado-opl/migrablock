package com.ws.ont.pojo.DTOs;

import com.ws.ont.enums.OltIdsAttr;
import lombok.Data;

import java.util.Map;

import static com.ws.cvlan.util.StringUtilSol.getLong;

@Data
public class OltIdsDto {
    private Long idOltIsp;
    private Long idOltNs;

    public OltIdsDto(Map<String, Object> result) {
        idOltIsp = getLong(result, OltIdsAttr.ID_OLT_ISP);
        idOltNs = getLong(result, OltIdsAttr.ID_OLT_NS);
    }
}
