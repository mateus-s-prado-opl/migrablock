package com.ws.cvlan.filter.validation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@AtLeastOneFieldNotEmpty(fields = {"stateAbbreviation", "stateName"}, message = "Either state abbreviation or state name must be filled")
@AtLeastOneFieldNotEmpty(fields = {"localityAbbreviation", "localityName"}, message = "Either locality abbreviation or locality name must be filled")
@AtLeastOneFieldNotEmpty(fields = {"oltName", "oltUid"}, message = "Either OLT name or OLT UID must be filled")
public abstract class BaseCvlanFilter {

    @Size(max = 2, message = "State Abbreviation must be less than or equal to 2 characters")
    @ApiModelProperty(value = "State abbreviation", example = "RS")
    private String stateAbbreviation; // siglaUf

    @ApiModelProperty(value = "State name", example = "RIO GRANDE DO SUL")
    private String stateName; // nomeUf

    @Size(max = 5, message = "Locality Abbreviation must be less than or equal to 5 characters")
    @ApiModelProperty(value = "Locality abbreviation", example = "NVP")
    private String localityAbbreviation; // siglaLocalidade

    @ApiModelProperty(value = "Locality name", example = "NOVA PETROPOLIS")
    private String localityName; // nomeLocalidade

    @ApiModelProperty(value = "OLT name", example = "NVP_I01_1_GPON_VLAN_100_101_102-ALC")
    private String oltName; // nomeOlt

    @ApiModelProperty(value = "OLT UID", example = "EQP0000000003384362")
    private String oltUid; // uidOlt

    @ApiModelProperty(value = "PON interface", example = "1/1/1/1")
    private String ponInterface; // interfacePON

    @ApiModelProperty(value = "ONT ID", example = "5")
    private Long ontId;

    @ApiModelProperty(value = "SVLAN", example = "100")
    private Integer svlan;

    @ApiModelProperty(value = "CVLAN", example = "200")
    private Integer cvlan;
}
