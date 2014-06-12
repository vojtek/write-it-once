package org.simart.writeonce.domain;

public class CtestBuilder {

    public static org.simart.writeonce.domain.CtestBuilder builder() {
        return new org.simart.writeonce.domain.CtestBuilder();
    }

    private java.lang.String dtestfield;

    private java.lang.String ctestfield;


    public org.simart.writeonce.domain.CtestBuilder dtestfield(java.lang.String dtestfield) {
        this.dtestfield = dtestfield;
        return this;
    }

    public org.simart.writeonce.domain.CtestBuilder ctestfield(java.lang.String ctestfield) {
        this.ctestfield = ctestfield;
        return this;
    }

    public org.simart.writeonce.domain.Ctest build() {
        final org.simart.writeonce.domain.Ctest data = new org.simart.writeonce.domain.Ctest();

        data.setDtestfield(this.dtestfield);

        data.setCtestfield(this.ctestfield);

        return data;
    }
}
