package org.simart.writeonce.common;

public interface Generator {

    String generate(Class<?> cls, String template) throws GeneratorException;

}
