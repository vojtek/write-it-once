package org.simart.writeonce.common;

import java.lang.annotation.Annotation;
import java.util.Map;

public interface AnnotationDescriptor extends TypeDescriptor, HasAnnotations, HasMethods {

    Annotation getAttributes();

    Map<String, Object> getAttribute();
}
