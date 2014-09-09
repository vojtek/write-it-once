package org.simart.writeonce.common;

import java.util.Collection;

public interface Help {

    String getRootType();

    String getBeanDescriptor();

    Collection<String> getMethods();

    String toString();
}
