package org.simart.writeonce.common;

import java.util.Map;

@Deprecated
public interface BeanClassDescriptor extends ClassDescriptor {
    @Deprecated
    BeanMethodDescriptor[] getGetters();

    @Deprecated
    Map<String, BeanMethodDescriptor> getGetter();

    @Deprecated
    Map<String, BeanMethodDescriptor> getGetterByField();

    @Deprecated
    BeanMethodDescriptor[] getSetters();

    @Deprecated
    Map<String, BeanMethodDescriptor> getSetter();

    @Deprecated
    Map<String, BeanMethodDescriptor> getSetterByField();
}
