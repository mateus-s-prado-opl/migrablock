package com.ws.cvlan.filter;

import lombok.Data;
import com.ws.cvlan.filter.validation.AtLeastOneFieldNotEmpty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AtLeastOneFieldNotEmpty(fields = {"stateAbbreviation", "stateName"}, message = "Either state abbreviation or state name must be filled")
@AtLeastOneFieldNotEmpty(fields = {"localityAbbreviation", "localityName"}, message = "Either locality abbreviation or locality name must be filled")
@AtLeastOneFieldNotEmpty(fields = {"oltName", "oltUid"}, message = "Either OLT name or OLT UID must be filled")
public class AddCvlanBlockFilter {

    @NotEmpty(message = "System origin is mandatory")
    private String systemOrigin;

    @NotEmpty(message = "Login is mandatory")
    private String login;

    //Um desses campos devem estar preenchidos
    private String stateAbbreviation;
    private String stateName;


    //Um desses campos devem estar preenchidos
    private String localityAbbreviation;
    private String localityName;

    //Um desses campos devem estar preenchidos
    private String oltName;
    private String oltUid;

    @NotEmpty(message = "interfacePON is mandatory")
    private String ponInterface;

    @NotNull(message = "OntId is mandatory")
    private Long ontId;

    @NotNull(message = "Svlan is mandatory")
    private Integer svlan;

    @NotNull(message = "Cvlan is mandatory")
    private Integer cvlan;

    @NotEmpty(message = "BlockReason is mandatory")
    private String blockReason;
}
