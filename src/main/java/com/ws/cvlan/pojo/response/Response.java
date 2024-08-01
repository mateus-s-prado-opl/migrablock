package com.ws.cvlan.pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ws.utils.enums.OperationResult;
import com.ws.utils.enums.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public abstract class Response {

    @ApiModelProperty(value = "ID of the response", example = "12345")
    Long id;

    @ApiModelProperty(value = "Message of the response", example = "Operation completed successfully")
    String message;

    @ApiModelProperty(value = "Status of the response", example = "SUCCESS")
    Status status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "Stack trace in case of error", example = "java.lang.NullPointerException: ...")
    String stackTrace;

    public boolean hasError() {
        return status == Status.ERROR;
    }

    public void setOperationResult(OperationResult errorType) {
        this.status = errorType.getStatus();
        this.message = errorType.getMessage();
    }
}
