package com.wk.simart.writeonce.domain;

import com.wk.simart.writeonce.common.BeanClassDescriptor;
import com.wk.simart.writeonce.common.ClassDescriptor;
import com.wk.simart.writeonce.common.TypeDescriptor;

public class TypeFactory extends AbstractDescriptorFactory {

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> cls, Object data) {
        T t = BeanClassDescriptor.class.isAssignableFrom(cls) ? (T) new ClassDescriptorImpl(getContext(), (Class<?>) data) : null;
        if (t != null) {
            return t;
        }
        t = ClassDescriptor.class.isAssignableFrom(cls) ? (T) new ClassDescriptorImpl(getContext(), (Class<?>) data) : null;
        if (t != null) {
            return t;
        }
        t = TypeDescriptor.class.isAssignableFrom(cls) ? (T) new ClassDescriptorImpl(getContext(), (Class<?>) data) : null;
        if (t != null) {
            return t;
        }
        return null;
    }

}
