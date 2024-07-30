package com.ws.cvlan.pojo;

import com.ws.utils.enums.Operation;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class AuditoriaLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userCreated;
    private Date dateCreated;
    private Operation operation;
    private String system;
    private String comments;
    private Long processId;

    public AuditoriaLog(
            String login,
            Operation operation,
            String system,
            String comments,
            Long processId
    ) {
        this.userCreated = login;
        this.dateCreated = new Date();
        this.operation = operation;
        this.system = system;
        this.comments = comments;
        this.processId = processId;
    }
}
