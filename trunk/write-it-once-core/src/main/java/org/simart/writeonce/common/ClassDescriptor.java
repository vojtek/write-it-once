package org.simart.writeonce.common;

import java.util.Map;

public interface ClassDescriptor extends TypeDescriptor, HasAnnotations, HasMethods, HasFields {

    Map<String, FieldDescriptor[]> getFieldsByAnnotation();

    Map<String, MethodDescriptor[]> getMethodsByAnnotation();

}
