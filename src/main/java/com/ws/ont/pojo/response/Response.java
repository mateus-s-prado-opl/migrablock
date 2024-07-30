package com.ws.ont.pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ws.utils.enums.OperationResult;
import com.ws.utils.enums.Status;
import lombok.Data;

@Data
public abstract class Response<T> {

    Long id;
    String message;
    Status status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String stackTrace;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;

    public boolean hasError() {
        return status == Status.ERROR;
    }

    public void setOperationResult(OperationResult errorType) {
        this.status = errorType.getStatus();
        this.message = errorType.getMessage();
    }
}
