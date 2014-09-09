package org.simart.writeonce.common;

public interface ColumnDescriptor extends ElementaryDescriptor {

    String getName();

    String getType();

    String getFullType();

    Integer getLength();

    Integer getPrecision();

    Integer getScale();

    Boolean isNullable();

}
