package org.simart.writeonce.domain;

import java.io.Serializable;

/**
 * Javadoc class comment
 * 
 * @author Wojtek
 *
 */
// inline class comment
/* block class comment */
@Describe("Annotation DSC")
@Builder(alias = "xxx")
public class Atest implements Serializable {

    private static final String DATA = "Static data";
    private static final long serialVersionUID = -3871878166637693521L;

    private Long id;
    private String atestField;
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
