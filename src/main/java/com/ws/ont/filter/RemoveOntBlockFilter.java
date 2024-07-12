package com.ws.ont.filter;

import com.ws.ont.filter.validation.BaseOntFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
public class RemoveOntBlockFilter extends BaseOntFilter {

    @Size(max = 100, message = "System must be less than or equal to 100 characters")
    @NotEmpty(message = "System origin is mandatory")
    private String systemOrigin;

    @Size(max = 50, message = "Login must be less than or equal to 50 characters")
    @NotEmpty(message = "Login is mandatory")
    private String login;

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

    @Size(max = 100, message = "RemoveBlockReason must be less than or equal to 100 characters")
    @NotEmpty(message = "BlockReason is mandatory")
    private String removeBlockReason;
}