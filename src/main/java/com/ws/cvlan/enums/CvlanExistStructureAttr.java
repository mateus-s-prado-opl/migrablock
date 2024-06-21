package com.ws.cvlan.enums;

import com.ws.cvlan.SolEnum;

public enum CvlanExistStructureAttr implements SolEnum {

    NAME ("SERVICE_NAME");

    final String fieldName;

    CvlanExistStructureAttr(String serviceName) {
        this.fieldName = serviceName;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }
}
