package org.simart.writeonce.common;

@Deprecated
public interface ColumnDescriptor extends ElementaryDescriptor {
    @Deprecated
    String getName();

    @Deprecated
    String getType();

    @Deprecated
    String getFullType();

    @Deprecated
    Integer getLength();

    @Deprecated
    Integer getPrecision();

    @Deprecated
    Integer getScale();

    @Deprecated
    Boolean isNullable();

}
