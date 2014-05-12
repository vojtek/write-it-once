package org.simart.writeonce.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Builder
@Entity
@Table(name = "A_TEST", schema = "TEST")
public class Atest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "AT_EST", insertable = true, nullable = false, length = 30)
    private String atestField;
    @ManyToOne
    private Btest btest;

    public String getAtestField() {
        return atestField;
    }

    public void setAtestField(String atestField) {
        this.atestField = atestField;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Btest getBtest() {
        return btest;
    }

    public void setBtest(Btest btest) {
        this.btest = btest;
    }

}
