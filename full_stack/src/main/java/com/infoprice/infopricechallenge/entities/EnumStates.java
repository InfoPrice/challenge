package com.infoprice.infopricechallenge.entities;

public enum EnumStates {

    AC("AC"), AL("AL"), AP("AP"), AM("AM"), BA("BA"),
    CE("CE"), ES("ES"), GO("GO"), MA("MA"), MT("MT"),
    MS("MS"), MG("MS"), PA("PA"), PB("PB"), PR("PR"),
    PE("PE"), PI("PI"), RJ("RJ"), RN("RN"), RS("RS"),
    RO("RO"), RR("RR"), SC("SC"), SP("SP"), SE("SE"),
    TO("TO"), DF("DF");

    private final String enumStates;
    EnumStates(String option) {
        enumStates = option;
    }
    public String getEnumState(){
        return enumStates;
    }



}
