package com.wk.simart.writeonce.domain;

import java.lang.reflect.Field;
import java.util.Map;

import com.wk.simart.writeonce.common.AnnotationDescriptor;
import com.wk.simart.writeonce.common.BeanClassDescriptor;
import com.wk.simart.writeonce.common.BeanFieldDescriptor;
import com.wk.simart.writeonce.common.BeanMethodDescriptor;
import com.wk.simart.writeonce.common.TypeDescriptor;

public class FieldDescriptorImpl implements BeanFieldDescriptor {

    private final Context context;
    private final Field field;

    public FieldDescriptorImpl(final Context context, final Field field) {
        super();
        this.context = context;
        this.field = field;
    }

    public AnnotationDescriptor[] getAnnotations() {
        // TODO Auto-generated method stub
        return null;
    }

    public Map<String, AnnotationDescriptor> getAnnotation() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getName() {
        return field.getName();
    }

    public TypeDescriptor getType() {
        return context.create(TypeDescriptor.class, field.getType());
    }

    public BeanMethodDescriptor getGetter() {
        final BeanClassDescriptor beanClassDescriptor = context.create(BeanClassDescriptor.class, field.getDeclaringClass());
        return beanClassDescriptor.getGetterByField().get(getName());
    }

    public BeanMethodDescriptor getSetter() {
        final BeanClassDescriptor beanClassDescriptor = context.create(BeanClassDescriptor.class, field.getDeclaringClass());
        return beanClassDescriptor.getSetterByField().get(getName());
    }

}
