package org.simart.writeonce.common;

import java.util.Map;

@Deprecated
public interface HasAnnotations {
    @Deprecated
    AnnotationDescriptor[] getAnnotations();

    @Deprecated
    Map<String, AnnotationDescriptor> getAnnotation();

}
