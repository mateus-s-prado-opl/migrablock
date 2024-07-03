package com.ws.cvlan.pojo;

import com.ws.cvlan.enums.ListCvlanBlockAttr;
import lombok.Data;

import java.util.Map;

import static com.ws.cvlan.util.StringUtilSol.getLong;
import static com.ws.cvlan.util.StringUtilSol.getString;

@Data
public class CvlanBlock {

    private Long id;
    private String userCreated;
    private String dateCreated;
    private String stateAbbreviation;
    private String stateName;
    private String localityAbbreviation;
    private String localityName;
    private String oltName;
    private String oltUid;
    private String ponInterface;
    private Long ontId;
    private Long svlan;
    private Long cvlan;
    private String comments;


    public CvlanBlock(Map<String, Object> block) {
        this.id = getLong(block, ListCvlanBlockAttr.PROCESS_ID);
        this.userCreated = getString(block, ListCvlanBlockAttr.USER_CREATED);
        this.dateCreated = getString(block, ListCvlanBlockAttr.DATE_CREATED);
        this.stateAbbreviation = getString(block, ListCvlanBlockAttr.STATE_ABBREVIATION);
        this.stateName = getString(block, ListCvlanBlockAttr.STATE_NAME);
        this.localityName = getString(block, ListCvlanBlockAttr.LOCALITY_NAME);
        this.localityAbbreviation = getString(block, ListCvlanBlockAttr.LOCALITY_ABBREVIATION);
        this.oltName = getString(block, ListCvlanBlockAttr.OLT_NAME);
        this.oltUid = getString(block, ListCvlanBlockAttr.OLT_UID);
        this.ponInterface = getString(block, ListCvlanBlockAttr.PON_INTERFACE);
        this.ontId = getLong(block, ListCvlanBlockAttr.ONT_ID);
        this.svlan = getLong(block, ListCvlanBlockAttr.SVLAN);
        this.cvlan = getLong(block, ListCvlanBlockAttr.CVLAN);
        this.comments = getString(block, ListCvlanBlockAttr.COMMENTS);
    }
}
