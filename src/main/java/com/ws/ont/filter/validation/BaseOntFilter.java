package com.ws.ont.filter.validation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

@EqualsAndHashCode()
@Data
@AtLeastOneFieldNotEmpty(fields = {"stateAbbreviation", "stateName"}, message = "Either state abbreviation or state name must be filled")
@AtLeastOneFieldNotEmpty(fields = {"localityAbbreviation", "localityName"}, message = "Either locality abbreviation or locality name must be filled")
@AtLeastOneFieldNotEmpty(fields = {"oltName", "oltUid"}, message = "Either OLT name or OLT UID must be filled")
public abstract class BaseOntFilter {

    @Size(max = 2, message = "State Abbreviation must be less than or equal to 2 characters")
    @ApiModelProperty(value = "State abbreviation", example = "RS")
    private String stateAbbreviation;

    @ApiModelProperty(value = "State name", example = "RIO GRANDE DO SUL")
    private String stateName;

    @Size(max = 5, message = "Locality Abbreviation must be less than or equal to 5 characters")
    @ApiModelProperty(value = "Locality abbreviation", example = "NVP")
    private String localityAbbreviation;

    @ApiModelProperty(value = "Locality name", example = "NOVA PETROPOLIS")
    private String localityName;

    @ApiModelProperty(value = "OLT name", example = "NVP_I01_1_GPON_VLAN_100_101_102-ALC")
    private String oltName;

    @ApiModelProperty(value = "OLT UID", example = "EQP0000000003384362")
    private String oltUid;

    @ApiModelProperty(value = "PON interface", required = true, example = "1/1/1/1")
    private String ponInterface;

    @ApiModelProperty(value = "ONT ID", required = true, example = "5")
    private Long ontId;
}
