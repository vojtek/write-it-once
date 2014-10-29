package org.simart.writeonce.common;

import java.util.Map;

public interface Descriptor<E extends Object> extends Map<String, Object> {

    E getData();

}
