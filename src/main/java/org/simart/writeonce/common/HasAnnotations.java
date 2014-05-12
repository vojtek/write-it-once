package org.simart.writeonce.common;

import java.util.Map;

public interface HasAnnotations {

    AnnotationDescriptor[] getAnnotations();

    Map<String, AnnotationDescriptor> getAnnotation();

}
