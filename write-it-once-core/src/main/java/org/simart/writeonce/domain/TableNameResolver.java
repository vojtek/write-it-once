package org.simart.writeonce.domain;

public interface TableNameResolver {

    String getName(Class<?> cls);

}
