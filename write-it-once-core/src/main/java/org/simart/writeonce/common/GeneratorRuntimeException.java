package org.simart.writeonce.common;

@SuppressWarnings("serial")
public class GeneratorRuntimeException extends RuntimeException {

    public GeneratorRuntimeException() {
	super();
    }

    public GeneratorRuntimeException(String arg0, Throwable arg1, boolean arg2,	    boolean arg3) {
	super(arg0, arg1, arg2, arg3);
    }

    public GeneratorRuntimeException(String arg0, Throwable arg1) {
	super(arg0, arg1);
    }

    public GeneratorRuntimeException(String arg0) {
	super(arg0);
    }

    public GeneratorRuntimeException(Throwable arg0) {
	super(arg0);
    }

}
