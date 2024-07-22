package com.ws.ont.pojo;

import com.ws.ont.enums.ListOntBlockAttr;
import lombok.Data;

import java.util.Map;

import static com.ws.cvlan.util.StringUtilSol.getLong;
import static com.ws.cvlan.util.StringUtilSol.getString;

@Data
public class OntBlock {

    //private Long id;
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

    public OntBlock(Map<String, Object> block) {
        //this.id = getLong(block, ListOntBlockAttr.PROCESS_ID);
        this.userCreated = getString(block, ListOntBlockAttr.USER_CREATED);
        this.dateCreated = getString(block, ListOntBlockAttr.DATE_CREATED);
        this.stateAbbreviation = getString(block, ListOntBlockAttr.STATE_ABBREVIATION);
        this.stateName = getString(block, ListOntBlockAttr.STATE_NAME);
        this.localityName = getString(block, ListOntBlockAttr.LOCALITY_NAME);
        this.localityAbbreviation = getString(block, ListOntBlockAttr.LOCALITY_ABBREVIATION);
        this.oltName = getString(block, ListOntBlockAttr.OLT_NAME);
        this.oltUid = getString(block, ListOntBlockAttr.OLT_UID);
        this.ponInterface = getString(block, ListOntBlockAttr.PON_INTERFACE);
        this.ontId = getLong(block, ListOntBlockAttr.ONT_ID);
    }
}
