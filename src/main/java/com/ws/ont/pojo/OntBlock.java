package com.ws.ont.pojo;

import com.ws.ont.enums.ListOntBlockAttr;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

import static com.ws.utils.StringUtilSol.getLong;
import static com.ws.utils.StringUtilSol.getString;

@Data
public class OntBlock {

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

    public OntBlock(Map<String, Object> block) {
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
