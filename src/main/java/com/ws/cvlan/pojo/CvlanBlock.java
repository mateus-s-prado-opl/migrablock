package com.ws.cvlan.pojo;

import com.ws.cvlan.enums.ListCvlanBlockAttr;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

import static com.ws.utils.StringUtilSol.getLong;
import static com.ws.utils.StringUtilSol.getString;

@Data
public class CvlanBlock {

    @ApiModelProperty(value = "ID of the CVLAN block", example = "12345")
    private Long id;

    @ApiModelProperty(value = "User who created the record", example = "admin")
    private String userCreated;

    @ApiModelProperty(value = "Date when the record was created", example = "2024-07-10")
    private String dateCreated;

    @ApiModelProperty(value = "State abbreviation", example = "RS")
    private String stateAbbreviation;

    @ApiModelProperty(value = "State name", example = "RIO GRANDE DO SUL")
    private String stateName;

    @ApiModelProperty(value = "Locality abbreviation", example = "NVP")
    private String localityAbbreviation;

    @ApiModelProperty(value = "Locality name", example = "NOVA PETROPOLIS")
    private String localityName;

    @ApiModelProperty(value = "OLT name", example = "NVP_I01_1_GPON_VLAN_100_101_102-ALC")
    private String oltName;

    @ApiModelProperty(value = "OLT UID", example = "EQP0000000003384362")
    private String oltUid;

    @ApiModelProperty(value = "PON interface", example = "1/1/1/1")
    private String ponInterface;

    @ApiModelProperty(value = "ONT ID", example = "5")
    private Long ontId;

    @ApiModelProperty(value = "SVLAN", example = "100")
    private Long svlan;

    @ApiModelProperty(value = "CVLAN", example = "200")
    private Long cvlan;

    @ApiModelProperty(value = "Comments or additional information", example = "No issues reported")
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
