package org.simart.writeonce.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Btest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "BTEST_FIELD", length = 10, nullable = false)
    private String btestfield;

    public String getBtestfield() {
        return btestfield;
    }

    public void setBtestfield(String btestfield) {
        this.btestfield = btestfield;
    }
}
