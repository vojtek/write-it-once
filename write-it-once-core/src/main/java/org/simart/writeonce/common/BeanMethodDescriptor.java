package org.simart.writeonce.common;

@Deprecated
public interface BeanMethodDescriptor extends MethodDescriptor, HasAnnotations {
    @Deprecated
    FieldDescriptor getField();

    @Deprecated
    BeanMethodDescriptor getGetter();

    @Deprecated
    BeanMethodDescriptor getSetter();

    @Deprecated
    boolean isGetter();

    @Deprecated
    boolean isSetter();

    @Deprecated
    boolean isBean();
}
