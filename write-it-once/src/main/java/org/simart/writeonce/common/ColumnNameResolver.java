package org.simart.writeonce.common;


public interface ColumnNameResolver {

    String getName(MethodDescriptor method);

    String getName(FieldDescriptor field);
}
