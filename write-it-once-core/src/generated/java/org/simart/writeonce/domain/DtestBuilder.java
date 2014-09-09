package org.simart.writeonce.domain;

public class DtestBuilder {

    public static org.simart.writeonce.domain.DtestBuilder builder() {
        return new org.simart.writeonce.domain.DtestBuilder();
    }

    private java.lang.String dtestfield;


    public org.simart.writeonce.domain.DtestBuilder dtestfield(java.lang.String dtestfield) {
        this.dtestfield = dtestfield;
        return this;
    }

    public org.simart.writeonce.domain.Dtest build() {
        final org.simart.writeonce.domain.Dtest data = new org.simart.writeonce.domain.Dtest();

        data.setDtestfield(this.dtestfield);

        return data;
    }
}
