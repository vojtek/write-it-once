package org.simart.writeonce.common;

@Deprecated
public interface BeanFieldDescriptor extends FieldDescriptor {
    @Deprecated
    BeanMethodDescriptor getGetter();

    @Deprecated
    BeanMethodDescriptor getSetter();

    @Deprecated
    Boolean hasGetter();

    @Deprecated
    Boolean hasSetter();
}
