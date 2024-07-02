package com.ws.cvlan.filter;

import com.ws.cvlan.filter.validation.Filter;
import lombok.Data;
import com.ws.cvlan.filter.validation.AtLeastOneFieldNotEmpty;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@AtLeastOneFieldNotEmpty(fields = {"stateAbbreviation", "stateName"}, message = "Either state abbreviation or state name must be filled")
@AtLeastOneFieldNotEmpty(fields = {"localityAbbreviation", "localityName"}, message = "Either locality abbreviation or locality name must be filled")
@AtLeastOneFieldNotEmpty(fields = {"oltName", "oltUid"}, message = "Either OLT name or OLT UID must be filled")
public class AddCvlanBlockFilter extends Filter {

    @Size(max = 100, message = "System must be less than or equal to 100 characters")
    @NotEmpty(message = "System origin is mandatory")
    private String systemOrigin;

    @Size(max = 50, message = "Login must be less than or equal to 100 characters")
    @NotEmpty(message = "Login is mandatory")
    private String login;

    @Size(max = 2, message = "State Abbreviation must be less than or equal to 2 characters")
    private String stateAbbreviation;
    private String stateName;

    @Size(max = 2, message = "State Abbreviation must be less than or equal to 2 characters")
    private String localityAbbreviation;
    private String localityName;

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

    @Size(max = 100, message = "BlockReason must be less than or equal to 100 characters")
    @NotEmpty(message = "BlockReason is mandatory")
    private String blockReason;
}
