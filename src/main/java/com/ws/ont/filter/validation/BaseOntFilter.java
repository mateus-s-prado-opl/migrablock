package com.ws.ont.filter.validation;

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
    private String stateAbbreviation;
    private String stateName;

    @Size(max = 5, message = "Locality Abbreviation must be less than or equal to 5 characters")
    private String localityAbbreviation;
    private String localityName;

    private String oltName;
    private String oltUid;
    private String ponInterface;
    private Long ontId;
}
