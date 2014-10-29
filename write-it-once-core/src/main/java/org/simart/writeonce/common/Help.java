package org.simart.writeonce.common;

import java.util.Collection;

@Deprecated
public interface Help {
    @Deprecated
    String getRootType();

    @Deprecated
    String getBeanDescriptor();

    @Deprecated
    Collection<String> getMethods();

    String toString();
}
