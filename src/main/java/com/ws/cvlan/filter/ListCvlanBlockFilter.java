package com.ws.cvlan.filter;

import com.ws.cvlan.filter.validation.AtLeastOneFieldNotEmpty;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@AtLeastOneFieldNotEmpty(fields = {"siglaUf", "nomeUf"}, message = "Either state abbreviation or state name must be filled")
@AtLeastOneFieldNotEmpty(fields = {"siglaLocalidade", "nomeLocalidade"}, message = "Either locality abbreviation or locality name must be filled")
@AtLeastOneFieldNotEmpty(fields = {"nomeOlt", "uidOlt"}, message = "Either OLT name or OLT UID must be filled")
public class ListCvlanBlockFilter {

    @Size(max = 2, message = "State Abbreviation must be less than or equal to 2 characters")
    private String siglaUf;
    private String nomeUf;

    private String siglaLocalidade;
    private String nomeLocalidade;

    private String nomeOlt;
    private String uidOlt;

    private String interfacePON;

    private Long ontId;

    private Integer svlan;

    private Integer cvlan;
}
