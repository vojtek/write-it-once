package org.simart.writeonce.common;

import java.util.Map;

public interface HasMethods {

    MethodDescriptor[] getMethods();

    Map<String, MethodDescriptor> getMethod();

}
