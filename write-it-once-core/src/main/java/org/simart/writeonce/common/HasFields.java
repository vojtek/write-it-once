package org.simart.writeonce.common;

import java.util.Map;

@Deprecated
public interface HasFields {
    @Deprecated
    FieldDescriptor[] getFields();

    @Deprecated
    Map<String, FieldDescriptor> getField();

}
