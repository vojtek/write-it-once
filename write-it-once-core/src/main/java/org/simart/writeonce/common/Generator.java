package org.simart.writeonce.common;

import java.util.Map;

public interface Generator {

    String generate(Class<?> cls, String template) throws GeneratorException;

    String generate(Class<?> cls, String template, Map<String, Object> customParameters) throws GeneratorException;

}
