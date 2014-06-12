package org.simart.writeonce.domain;

public class AtestBuilder {

    public static org.simart.writeonce.domain.AtestBuilder builder() {
        return new org.simart.writeonce.domain.AtestBuilder();
    }

    private java.lang.String atestField;

    private org.simart.writeonce.domain.Btest btest;

    private java.lang.Long id;


    public org.simart.writeonce.domain.AtestBuilder atestField(java.lang.String atestField) {
        this.atestField = atestField;
        return this;
    }

    public org.simart.writeonce.domain.AtestBuilder btest(org.simart.writeonce.domain.Btest btest) {
        this.btest = btest;
        return this;
    }

    public org.simart.writeonce.domain.AtestBuilder id(java.lang.Long id) {
        this.id = id;
        return this;
    }

    public org.simart.writeonce.domain.Atest build() {
        final org.simart.writeonce.domain.Atest data = new org.simart.writeonce.domain.Atest();

        data.setAtestField(this.atestField);

        data.setBtest(this.btest);

        data.setId(this.id);

        return data;
    }
}
