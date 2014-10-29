package org.simart.writeonce.common;

import java.util.Map;

/**
 * @deprecated Don't use this s...t :) See FlexibleGenerator instead!
 * @author Wojtek
 *
 */
@Deprecated
public interface Generator {
    @Deprecated
    String generate(Class<?> cls, String template) throws GeneratorException;

    @Deprecated
    String generate(Class<?> cls, String template, Map<String, Object> customParameters) throws GeneratorException;

}
