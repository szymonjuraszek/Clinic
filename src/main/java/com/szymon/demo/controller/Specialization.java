package com.szymon.demo.controller;

import java.util.ArrayList;
import java.util.List;

public enum Specialization {
    PEDIATRA,
    OKULISTA,
    PSYCHOLOG,
    DENDYSTA,
    CHIRURG,
    ORTOPEDA,
    DERMATOLOG;

    public static List<String> getEnumsInLowerCase(){
        List<String> enums = new ArrayList<>();

        for (Specialization data: values()) {
            enums.add(data.name().toLowerCase());
        }
        return enums;
    }
}
