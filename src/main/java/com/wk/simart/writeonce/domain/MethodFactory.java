package com.wk.simart.writeonce.domain;

import java.lang.reflect.Method;

import com.wk.simart.writeonce.common.DescriptorFactory;
import com.wk.simart.writeonce.common.MethodDescriptor;

public class MethodFactory implements DescriptorFactory {

    private Context context;

    public void init(Context context) {
        this.context = context;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> cls, Object data) {
        return MethodDescriptor.class.isAssignableFrom(cls) ? (T) new MethodDescriptorImpl(context, (Method) data) : null;
    }
}
