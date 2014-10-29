package org.simart.writeonce.common;

import java.lang.annotation.Annotation;
import java.util.Map;

@Deprecated
public interface AnnotationDescriptor extends TypeDescriptor, HasAnnotations, HasMethods, ElementaryDescriptor {
    @Deprecated
    Annotation getAttributes();

    @Deprecated
    Map<String, Object> getAttribute();
}
