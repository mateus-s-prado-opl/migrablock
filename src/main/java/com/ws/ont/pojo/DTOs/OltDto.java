package com.ws.ont.pojo.DTOs;

import com.ws.ont.enums.OltAttributesAttr;
import lombok.Data;

import java.util.Map;

import static com.ws.cvlan.util.StringUtilSol.getLong;
import static com.ws.cvlan.util.StringUtilSol.getString;

@Data
public class OltDto {

    private Long oltPtpid;
    private String oltPtpName;
    private Long oltTtpId;
    private Long oltCtpId;
    private String oltCtpName;

    public OltDto(Map<String, Object> res) {
        this.oltPtpid = getLong(res, OltAttributesAttr.OLT_PTP_ID);
        this.oltPtpName = getString(res, OltAttributesAttr.OLT_PTP_NAME);
        this.oltTtpId = getLong(res, OltAttributesAttr.OLT_TTP_ID);
        this.oltCtpId = getLong(res, OltAttributesAttr.OLT_CTP_ID);
        this.oltCtpName = getString(res, OltAttributesAttr.OLT_CTP_NAME);
    }

    public void checkOltData(OltDto oltDto, String oltPortNsId, String ontId) {
        if (oltDto.getOltPtpid() == null) {
            throw new IllegalArgumentException("Cannot find ptp " + oltPortNsId);
        }
        if (oltDto.getOltCtpId() != null) {
            throw new IllegalArgumentException("Already exists CTP.GPON to ptp " + oltPortNsId + " ont_id " + ontId);
        }
        if (oltDto.getOltTtpId() != null) {
            Long ttpId = oltDto.getOltTtpId();
            System.out.println("TTP ja existe " + ttpId);
        }
    }
}
