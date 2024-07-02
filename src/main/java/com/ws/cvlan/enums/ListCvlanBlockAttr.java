package com.ws.cvlan.enums;

import com.ws.cvlan.SolEnum;

public enum ListCvlanBlockAttr implements SolEnum {

    PROCESS_ID("PROCESS_ID"),
    USER_CREATED("USER_CREATED"),
    DATE_CREATED("DATE_CREATED"),
    STATE_ABBREVIATION("CTL_UF"),
    LOCALITY_NAME("CTL_CIDADE"),
    OLT_NAME("OLT"),
    ONT_ID("ONT_ID"),
    SVLAN("SVLAN"),
    CVLAN("CVLAN"),
    COMMENTS("COMMENTS");

    //TODO: Inserir esses campos na consulta
        //    NOME_UF("NOME_UF"),
        //    SIGLA_LOCALIDADE("SIGLA_LOCALIDADE"),
        //    UID_OLT("UID_OLT"),
        //    INTERFACE_PORTA_PON("INTERFACE_PORTA_PON"),
        //    ONT_ID_CTP_GPON("ONT_ID_CTP_GPON");




    private final String fieldName;

    ListCvlanBlockAttr(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String toString() {
        return this.name() + ": " + this.fieldName;
    }

}
