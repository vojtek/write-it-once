package org.simart.writeonce.domain;

import static org.simart.writeonce.utils.StringUtils.uncapitalize;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import org.simart.writeonce.common.AnnotationDescriptor;
import org.simart.writeonce.common.BeanClassDescriptor;
import org.simart.writeonce.common.BeanMethodDescriptor;
import org.simart.writeonce.common.FieldDescriptor;
import org.simart.writeonce.common.TypeDescriptor;

public class MethodDescriptorImpl implements BeanMethodDescriptor {

    private final Context context;
    private final Method method;

    public MethodDescriptorImpl(Context context, Method method) {
        super();
        this.context = context;
        this.method = method;
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
        return this.method.getName();
    }

    public TypeDescriptor getType() {
        return context.create(TypeDescriptor.class, method.getReturnType());
    }

    public TypeDescriptor[] getParameters() {
        final Parameter[] parameters = method.getParameters();
        return context.create(TypeDescriptor[].class, parameters);
    }

    public BeanMethodDescriptor getGetter() {
        if (isGetter()) {
            return this;
        }
        return context.create(BeanClassDescriptor.class, method.getDeclaringClass()).getGetter().get(getField().getName());
    }

    public BeanMethodDescriptor getSetter() {
        if (isSetter()) {
            return this;
        }
        return context.create(BeanClassDescriptor.class, method.getDeclaringClass()).getSetter().get(getField().getName());
    }

    public FieldDescriptor getField() {
        if (!isBean()) {
            return null;
        }
        return context.create(BeanClassDescriptor.class, method.getDeclaringClass()).getField().get(uncapitalize(getName().substring(3)));
    }

    public boolean isGetter() {
        return getName().startsWith("get");
    }

    public boolean isSetter() {
        return getName().startsWith("set");
    }

    public boolean isBean() {
        return isGetter() || isSetter();
    }

}
