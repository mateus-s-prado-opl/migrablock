package com.ws.cvlan.pojo;

import com.ws.cvlan.enums.OperationResult ;
import com.ws.cvlan.enums.Status;
import lombok.Data;

@Data
public class AddCvlanBlock {
    Long id;
    String message;
    Status status;

    public boolean hasError() {
        return status == Status.ERROR;
    }

    public void setOperationResult(OperationResult errorType) {
        this.status = errorType.getStatus();
        this.message = errorType.getMessage();
    }
}
