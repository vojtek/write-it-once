package com.wk.simart.writeonce.domain;

public enum Enumek {
    OPTION_1("O1"),
    OPTION_2("O2");

    private final String name;

    private Enumek(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
