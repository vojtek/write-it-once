package com.wk.simart.writeonce.common;

import java.util.Map;

public interface HasFields {

    FieldDescriptor[] getFields();

    Map<String, FieldDescriptor> getField();

}
