package com.wk.simart.writeonce.domain;

public class DtestBuilder {

    public static com.wk.simart.writeonce.domain.DtestBuilder builder() {
        return new com.wk.simart.writeonce.domain.DtestBuilder();
    }

    private java.lang.String dtestfield;


    public com.wk.simart.writeonce.domain.DtestBuilder dtestfield(java.lang.String dtestfield) {
        this.dtestfield = dtestfield;
        return this;
    }

    public org.simart.writeonce.domain.Dtest build() {
        final org.simart.writeonce.domain.Dtest data = new org.simart.writeonce.domain.Dtest();

        data.setDtestfield(this.dtestfield);

        return data;
    }
}
