package org.simart.writeonce.common;

import java.util.Map;

@Deprecated
public interface ClassDescriptor extends TypeDescriptor, HasAnnotations, HasMethods, HasFields {
    @Deprecated
    Map<String, FieldDescriptor[]> getFieldsByAnnotation();

    @Deprecated
    Map<String, MethodDescriptor[]> getMethodsByAnnotation();

}
