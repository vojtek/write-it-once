package org.simart.writeonce.common;

@SuppressWarnings("serial")
public class GeneratorException extends Exception {

    public GeneratorException() {
        super();
    }

    public GeneratorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneratorException(String message) {
        super(message);
    }

    public GeneratorException(Throwable cause) {
        super(cause);
    }

}
