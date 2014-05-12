package com.wk.simart.writeonce.common;

import com.wk.simart.writeonce.domain.Context;

public interface DescriptorFactory {

    void init(Context context);

    <T> T create(Class<T> cls, Object data);
}
