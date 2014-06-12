package org.simart.writeonce.common;


public interface BeanMethodDescriptor extends MethodDescriptor, HasAnnotations {

    FieldDescriptor getField();

    BeanMethodDescriptor getGetter();

    BeanMethodDescriptor getSetter();

    boolean isGetter();

    boolean isSetter();

    boolean isBean();
}
