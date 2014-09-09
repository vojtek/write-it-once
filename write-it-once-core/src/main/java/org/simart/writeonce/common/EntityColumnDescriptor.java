package org.simart.writeonce.common;

public interface EntityColumnDescriptor extends ElementaryDescriptor {

    boolean isEntity();

    ColumnDescriptor getColumn();

}
