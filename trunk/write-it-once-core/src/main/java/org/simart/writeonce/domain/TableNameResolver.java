package org.simart.writeonce.domain;

import org.simart.writeonce.common.ClassDescriptor;

public interface TableNameResolver {

    String getName(ClassDescriptor cls);

}
