package com.ws.ont.enums;

import com.ws.cvlan.SolEnum;

public enum OntExistStructureAttr implements SolEnum {

    CTP_ID("CTP_ID");

    final String fieldName;

    OntExistStructureAttr(String serviceName) {
        this.fieldName = serviceName;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }
}
