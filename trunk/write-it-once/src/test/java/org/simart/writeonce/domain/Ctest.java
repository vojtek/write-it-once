package org.simart.writeonce.domain;

@Builder
public class Ctest extends Dtest {

    private String ctestfield;

    public String getCtestfield() {
        return ctestfield;
    }

    public void setCtestfield(String ctestfield) {
        this.ctestfield = ctestfield;
    }
}
