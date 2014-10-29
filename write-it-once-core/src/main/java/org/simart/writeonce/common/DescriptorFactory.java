package org.simart.writeonce.common;

import org.simart.writeonce.domain.Context;

@Deprecated
public interface DescriptorFactory {
    @Deprecated
    void init(Context context);

    @Deprecated
    <T> T create(Class<T> cls, Object data);
}
