package org.simart.writeonce.common;

import org.simart.writeonce.domain.Context;

public interface DescriptorFactory {

    void init(Context context);

    <T> T create(Class<T> cls, Object data);
}
