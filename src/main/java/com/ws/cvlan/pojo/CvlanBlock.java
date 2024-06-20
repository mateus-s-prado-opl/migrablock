package com.ws.cvlan.pojo;

import com.ws.cvlan.enums.Status;
import lombok.Data;

@Data
public class CvlanBlock {
    Long id;
    String message;
    Status status;
}
