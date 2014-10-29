package org.simart.writeonce.common;

public interface Action<E extends Object> {

    Object execute(E data);

}
