package org.simart.writeonce.common;

@Deprecated
public interface FieldDescriptor extends HasAnnotations, ElementaryDescriptor {
    @Deprecated
    String getName();

    @Deprecated
    TypeDescriptor getType();

}
