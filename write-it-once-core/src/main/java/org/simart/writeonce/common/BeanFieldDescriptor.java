package org.simart.writeonce.common;

public interface BeanFieldDescriptor extends FieldDescriptor {

    BeanMethodDescriptor getGetter();

    BeanMethodDescriptor getSetter();

    Boolean hasGetter();

    Boolean hasSetter();
}
