package com.ws.cvlan.pojo.DTOs;

import com.ws.cvlan.enums.CheckCvlanBlockExistsAttr;
import lombok.Data;

import java.util.List;
import java.util.Map;

import static com.ws.cvlan.util.StringUtilSol.getLong;
import static com.ws.cvlan.util.StringUtilSol.getString;

@Data
public class CreateMessageCheckCvlanBlockExistsDTO {

    private long processId;
    private String comments;
    private String userCreated;
    private String dateCreated;

    private boolean exist = false;


    public CreateMessageCheckCvlanBlockExistsDTO(List<Map<String, Object>> map) {

        if(!map.isEmpty()){
            this.processId = getLong(map.get(0), CheckCvlanBlockExistsAttr.PROCESS_ID);
            this.comments = getString(map.get(0), CheckCvlanBlockExistsAttr.COMMENTS);
            this.userCreated = getString(map.get(0), CheckCvlanBlockExistsAttr.USER);
            this.dateCreated = getString(map.get(0), CheckCvlanBlockExistsAttr.DATE_CREATED);
            this.setExist(true);
        }


    }

    public String getMessage() {
        return "The CVLAN already exists. Please review the details below: " +
                " Comments: " + comments +
                " User Created: " + userCreated +
                " Date Created: " + dateCreated;
    }



}
