package org.simart.writeonce.common;

import org.simart.writeonce.application.Context;

public interface Action<E extends Object> {

    Object execute(E data, Context context);

}
