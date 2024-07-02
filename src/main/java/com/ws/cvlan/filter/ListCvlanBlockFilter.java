package com.ws.cvlan.filter;

import com.ws.cvlan.filter.validation.AtLeastOneFieldNotEmpty;
import com.ws.cvlan.filter.validation.Filter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@AtLeastOneFieldNotEmpty(fields = {"stateAbbreviation", "stateName"}, message = "Either state abbreviation or state name must be filled")
@AtLeastOneFieldNotEmpty(fields = {"localityAbbreviation", "localityName"}, message = "Either locality abbreviation or locality name must be filled")
@AtLeastOneFieldNotEmpty(fields = {"oltName", "oltUid"}, message = "Either OLT name or OLT UID must be filled")
public class ListCvlanBlockFilter extends Filter {

    @Size(max = 2, message = "State Abbreviation must be less than or equal to 2 characters")
    private String stateAbbreviation; // siglaUf
    private String stateName; // nomeUf

    private String localityAbbreviation; // siglaLocalidade
    private String localityName; // nomeLocalidade

    private String oltName; // nomeOlt
    private String oltUid; // uidOlt

    private String ponInterface; // interfacePON

    private Long ontId;

    private Integer svlan;

    private Integer cvlan;
}
