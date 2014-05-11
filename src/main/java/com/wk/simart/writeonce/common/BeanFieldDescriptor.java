package com.wk.simart.writeonce.common;

public interface BeanFieldDescriptor extends FieldDescriptor {

    BeanMethodDescriptor getGetter();

    BeanMethodDescriptor getSetter();
}
