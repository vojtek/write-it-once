package org.simart.writeonce.common;

public interface FieldDescriptor extends HasAnnotations, ElementaryDescriptor {

    String getName();

    TypeDescriptor getType();

}
