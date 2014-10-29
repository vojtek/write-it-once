package org.simart.writeonce.common;

@Deprecated
public interface MethodDescriptor extends HasAnnotations, ElementaryDescriptor {
    @Deprecated
    String getName();

    @Deprecated
    TypeDescriptor getType();

    @Deprecated
    ParameterDescriptor[] getParameters();

}
