package org.simart.writeonce.common;

public interface EntityDescriptor extends ClassDescriptor {

    Boolean isEntity();

    TableDescriptor getTable();

}
