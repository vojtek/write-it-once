package org.simart.writeonce.common;

public interface ParameterDescriptor extends ElementaryDescriptor {

    int getPosition();

    String getName();

    ClassDescriptor getType();
}
