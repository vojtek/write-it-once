package org.simart.writeonce.common;

import java.util.Map;

public interface BeanClassDescriptor extends ClassDescriptor {

    BeanMethodDescriptor[] getGetters();

    Map<String, BeanMethodDescriptor> getGetter();

    Map<String, BeanMethodDescriptor> getGetterByField();

    BeanMethodDescriptor[] getSetters();

    Map<String, BeanMethodDescriptor> getSetter();

    Map<String, BeanMethodDescriptor> getSetterByField();
}
