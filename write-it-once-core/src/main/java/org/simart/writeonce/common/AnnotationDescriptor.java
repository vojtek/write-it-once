package org.simart.writeonce.common;

import java.lang.annotation.Annotation;
import java.util.Map;

public interface AnnotationDescriptor extends TypeDescriptor, HasAnnotations, HasMethods, ElementaryDescriptor {

    Annotation getAttributes();

    Map<String, Object> getAttribute();
}
