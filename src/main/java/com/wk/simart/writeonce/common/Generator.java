package com.wk.simart.writeonce.common;

public interface Generator {

    String generate(Class<?> cls, String template) throws GeneratorException;

}
