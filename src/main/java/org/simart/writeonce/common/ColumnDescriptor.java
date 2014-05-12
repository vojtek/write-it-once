package org.simart.writeonce.common;

public interface ColumnDescriptor {

    String getName();

    String getType();

    String getFullType();

    Integer getLength();

    Integer getPrecision();

    Integer getScale();

    Boolean isNullable();

}
