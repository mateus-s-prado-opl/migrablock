package com.ws.cvlan.pojo.DTOs;

import com.ws.cvlan.filter.validation.BaseCvlanFilter;
import lombok.Data;

@Data
public class CheckCvlanBlockExistsDTO {

    private Integer svlan;
    private Integer cvlan;
    private String stateAbbreviation;
    private String stateName;
    private String localityAbbreviation;
    private String localityName;
    private String oltName;
    private String oltUid;
    private String ponInterface;
    private Long ontId;

    public CheckCvlanBlockExistsDTO(BaseCvlanFilter filter) {
        this.svlan = filter.getSvlan();
        this.cvlan = filter.getCvlan();
        this.stateAbbreviation = filter.getStateAbbreviation();
        this.stateName = filter.getStateName();
        this.localityAbbreviation = filter.getLocalityAbbreviation();
        this.localityName = filter.getLocalityName();
        this.oltName = filter.getOltName();
        this.oltUid = filter.getOltUid();
        this.ponInterface = filter.getPonInterface();
        this.ontId = filter.getOntId();
    }
}
