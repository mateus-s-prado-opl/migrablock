package com.ws.cvlan.pojo;

import com.ws.cvlan.enums.Status;
import lombok.Data;

@Data
public class RemoveCvlanBlock {
    Long id;
    String message;
    Status status;
}
