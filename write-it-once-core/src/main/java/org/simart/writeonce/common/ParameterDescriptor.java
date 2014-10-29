package org.simart.writeonce.common;

@Deprecated
public interface ParameterDescriptor extends ElementaryDescriptor {
    @Deprecated
    int getPosition();

    @Deprecated
    String getName();

    @Deprecated
    ClassDescriptor getType();
}
