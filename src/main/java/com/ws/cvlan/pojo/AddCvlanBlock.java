package com.ws.cvlan.pojo;

import com.ws.cvlan.enums.Status;
import lombok.Data;

@Data
public class AddCvlanBlock {
    Long id;
    String message;
    Status status;


    public boolean hasError(){
        return Status.ERROR.equals(status);
    }

}
