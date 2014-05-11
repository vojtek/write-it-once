package com.wk.simart.writeonce.domain;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.getAllMethods;
import static org.reflections.ReflectionUtils.withPrefix;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.wk.simart.writeonce.common.AnnotationDescriptor;
import com.wk.simart.writeonce.common.BeanClassDescriptor;
import com.wk.simart.writeonce.common.BeanMethodDescriptor;
import com.wk.simart.writeonce.common.FieldDescriptor;
import com.wk.simart.writeonce.common.MethodDescriptor;

public class ClassDescriptorImpl extends TypeDescriptorImpl implements BeanClassDescriptor {

    private final Function<Method, BeanMethodDescriptor> method2descriptor;
    private final Function<Field, FieldDescriptor> field2descriptor;

    public ClassDescriptorImpl(final Context context, final Class<?> cls) {
        super(context, cls);
        this.method2descriptor = new Function<Method, BeanMethodDescriptor>() {

            public BeanMethodDescriptor apply(Method input) {
                return context.create(BeanMethodDescriptor.class, input);
            }
        };
        this.field2descriptor = new Function<Field, FieldDescriptor>() {

            public FieldDescriptor apply(Field input) {
                return context.create(FieldDescriptor.class, input);
            }
        };
    }

    @Override
    public String getName() {
        return cls.getName();
    }

    @Override
    public String getShortName() {
        return cls.getSimpleName();
    }

    public AnnotationDescriptor[] getAnnotations() {
        // TODO Auto-generated method stub
        return null;
    }

    public Map<String, AnnotationDescriptor> getAnnotation() {
        // TODO Auto-generated method stub
        return null;
    }

    public MethodDescriptor[] getMethods() {
        @SuppressWarnings("unchecked")
        final Set<Method> methods = getAllMethods(this.cls);
        return Collections2.transform(methods, method2descriptor).toArray(new MethodDescriptor[methods.size()]);
    }

    public Map<String, MethodDescriptor> getMethod() {
        @SuppressWarnings("unchecked")
        final Set<Method> methods = getAllMethods(this.cls);
        final Map<String, MethodDescriptor> result = Maps.newHashMap();
        for (Method method : methods) {
            result.put(method.getName(), method2descriptor.apply(method));
        }
        return ImmutableMap.copyOf(result);
    }

    public FieldDescriptor[] getFields() {
        @SuppressWarnings("unchecked")
        final Set<Field> fields = getAllFields(cls);
        return Collections2.transform(fields, field2descriptor).toArray(new FieldDescriptor[fields.size()]);
    }

    public Map<String, FieldDescriptor> getField() {
        @SuppressWarnings("unchecked")
        final Set<Field> fields = getAllFields(this.cls);
        final Map<String, FieldDescriptor> result = Maps.newHashMap();
        for (Field field : fields) {
            result.put(field.getName(), field2descriptor.apply(field));
        }
        return ImmutableMap.copyOf(result);
    }

    public BeanMethodDescriptor[] getGetters() {
        @SuppressWarnings("unchecked")
        final Set<Method> methods = getAllMethods(this.cls, withPrefix("get"));
        return Collections2.transform(methods, method2descriptor).toArray(new BeanMethodDescriptor[methods.size()]);
    }

    public Map<String, BeanMethodDescriptor> getGetter() {
        final BeanMethodDescriptor[] methods = getGetters();
        final Map<String, BeanMethodDescriptor> result = Maps.newHashMap();
        for (BeanMethodDescriptor method : methods) {
            result.put(method.getName(), method);
        }
        return ImmutableMap.copyOf(result);
    }

    public BeanMethodDescriptor[] getSetters() {
        @SuppressWarnings("unchecked")
        final Set<Method> methods = getAllMethods(this.cls, withPrefix("set"));
        return Collections2.transform(methods, method2descriptor).toArray(new BeanMethodDescriptor[methods.size()]);
    }

    public Map<String, BeanMethodDescriptor> getSetter() {
        final BeanMethodDescriptor[] methods = getSetters();
        final Map<String, BeanMethodDescriptor> result = Maps.newHashMap();
        for (BeanMethodDescriptor method : methods) {
            result.put(method.getName(), method);
        }
        return ImmutableMap.copyOf(result);
    }

    public Map<String, BeanMethodDescriptor> getGetterByField() {
        final BeanMethodDescriptor[] methods = getGetters();
        final Map<String, BeanMethodDescriptor> result = Maps.newHashMap();
        for (BeanMethodDescriptor method : methods) {
            result.put(method.getField().getName(), method);
        }
        return ImmutableMap.copyOf(result);
    }

    public Map<String, BeanMethodDescriptor> getSetterByField() {
        final BeanMethodDescriptor[] methods = getSetters();
        final Map<String, BeanMethodDescriptor> result = Maps.newHashMap();
        for (BeanMethodDescriptor method : methods) {
            result.put(method.getField().getName(), method);
        }
        return ImmutableMap.copyOf(result);
    }

}
