package com.ws.ont.pojo;

import com.ws.ont.enums.Operation;
import com.ws.ont.filter.validation.BaseOntFilter;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class AuditoriaLogOnt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userCreated;
    private Date dateCreated;
    private String stateAbbreviation;
    private String stateName;
    private String localityAbbreviation;
    private String localityName;
    private String oltName;
    private String oltUid;
    private String ponInterface;
    private Long ontId;
    private String comments;
    private Operation operation;
    private String system;
    private Long idProcess;

    public AuditoriaLogOnt(BaseOntFilter filter, String login, String system, String comments, Operation operation, Long id) {
        this.userCreated = login;
        this.dateCreated = new Date();
        this.stateAbbreviation = filter.getStateAbbreviation();
        this.stateName = filter.getStateName();
        this.localityAbbreviation = filter.getLocalityAbbreviation();
        this.localityName = filter.getLocalityName();
        this.oltName = filter.getOltName();
        this.oltUid = filter.getOltUid();
        this.ponInterface = filter.getPonInterface();
        this.ontId = filter.getOntId();
        this.comments = comments;
        this.operation = operation;
        this.system = system;
        this.idProcess = id;
    }
}
