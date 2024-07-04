package com.ws.cvlan.pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ws.cvlan.enums.OperationResult;
import com.ws.cvlan.enums.Status;
import lombok.Data;

@Data
public class Response {

    Long id;
    String message;
    Status status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String stackTrace;

    public boolean hasError() {
        return status == Status.ERROR;
    }

    public void setOperationResult(OperationResult errorType) {
        this.status = errorType.getStatus();
        this.message = errorType.getMessage();
    }
}
