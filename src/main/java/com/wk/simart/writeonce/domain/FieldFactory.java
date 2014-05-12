package com.wk.simart.writeonce.domain;

import java.lang.reflect.Field;

import com.wk.simart.writeonce.common.DescriptorFactory;
import com.wk.simart.writeonce.common.FieldDescriptor;

public class FieldFactory implements DescriptorFactory {

    private Context context;

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> cls, Object data) {
        return (FieldDescriptor.class.isAssignableFrom(cls) ? (T) new FieldDescriptorImpl(context, (Field) data) : null);
    }

    public void init(Context context) {
        this.context = context;
    }

}
