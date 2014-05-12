package com.wk.simart.writeonce.common;

public interface MethodDescriptor extends HasAnnotations {

    String getName();

    TypeDescriptor getType();

    TypeDescriptor[] getParameters();

}
