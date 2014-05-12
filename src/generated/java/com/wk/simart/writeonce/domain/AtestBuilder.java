package com.wk.simart.writeonce.domain;

public class AtestBuilder {

    public static com.wk.simart.writeonce.domain.AtestBuilder builder() {
        return new com.wk.simart.writeonce.domain.AtestBuilder();
    }

    private java.lang.String atestField;

    private java.lang.Long id;

    private org.simart.writeonce.domain.Btest btest;


    public com.wk.simart.writeonce.domain.AtestBuilder atestField(java.lang.String atestField) {
        this.atestField = atestField;
        return this;
    }

    public com.wk.simart.writeonce.domain.AtestBuilder id(java.lang.Long id) {
        this.id = id;
        return this;
    }

    public com.wk.simart.writeonce.domain.AtestBuilder btest(org.simart.writeonce.domain.Btest btest) {
        this.btest = btest;
        return this;
    }

    public org.simart.writeonce.domain.Atest build() {
        final org.simart.writeonce.domain.Atest data = new org.simart.writeonce.domain.Atest();

        data.setAtestField(this.atestField);

        data.setId(this.id);

        data.setBtest(this.btest);

        return data;
    }
}
