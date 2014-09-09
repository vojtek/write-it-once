package org.simart.writeonce.common;

public interface MethodDescriptor extends HasAnnotations, ElementaryDescriptor {

    String getName();

    TypeDescriptor getType();

    ParameterDescriptor[] getParameters();

}
