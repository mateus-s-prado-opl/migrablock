package com.ws.cvlan.filter;

import com.ws.cvlan.filter.validation.AtLeastOneFieldNotEmpty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AtLeastOneFieldNotEmpty(fields = {"stateAbbreviation", "stateName"}, message = "Either state abbreviation or state name must be filled")
@AtLeastOneFieldNotEmpty(fields = {"localityAbbreviation", "localityName"}, message = "Either locality abbreviation or locality name must be filled")
@AtLeastOneFieldNotEmpty(fields = {"oltName", "oltUid"}, message = "Either OLT name or OLT UID must be filled")
public class RemoveCvlanBlockFilter {

    @Size(max = 100, message = "System must be less than or equal to 100 characters")
    @NotEmpty(message = "System origin is mandatory")
    private String systemOrigin;

    @Size(max = 50, message = "Login must be less than or equal to 100 characters")
    @NotEmpty(message = "Login is mandatory")
    private String login;


    private String stateAbbreviation;
    private String stateName;

    private String localityAbbreviation;
    private String localityName;

    private String oltName;
    private String oltUid;

    @NotEmpty(message = "interfacePON is mandatory")
    private String ponInterface;

    @NotNull(message = "OntId is mandatory")
    private Long ontId;

    @Size(max = 100, message = "RemovalBlockReason must be less than or equal to 100 characters")
    @NotEmpty(message = "BlockReason is mandatory")
    private String removalBlockReason;
}
