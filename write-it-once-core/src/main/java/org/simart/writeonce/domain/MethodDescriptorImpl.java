package org.simart.writeonce.domain;

import static org.simart.writeonce.utils.StringUtils.uncapitalize;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.simart.writeonce.common.AnnotationDescriptor;
import org.simart.writeonce.common.BeanClassDescriptor;
import org.simart.writeonce.common.BeanMethodDescriptor;
import org.simart.writeonce.common.ColumnDescriptor;
import org.simart.writeonce.common.EntityColumnDescriptor;
import org.simart.writeonce.common.EntityTableDescriptor;
import org.simart.writeonce.common.FieldDescriptor;
import org.simart.writeonce.common.Help;
import org.simart.writeonce.common.ParameterDescriptor;
import org.simart.writeonce.common.TypeDescriptor;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

public class MethodDescriptorImpl implements BeanMethodDescriptor, EntityColumnDescriptor {

    private final Context context;
    private final Method method;
    private final Function<Annotation, AnnotationDescriptor> annotation2descriptor;

    public MethodDescriptorImpl(final Context context, Method method) {
        super();
        this.context = context;
        this.method = method;
        this.annotation2descriptor = new Function<Annotation, AnnotationDescriptor>() {
            @Override
            public AnnotationDescriptor apply(Annotation input) {
                return context.create(AnnotationDescriptor.class, input);
            }
        };
    }

    @Override
    public AnnotationDescriptor[] getAnnotations() {
        final Set<Annotation> annotations = ImmutableSet.copyOf(this.method.getAnnotations());
        final Collection<AnnotationDescriptor> annotationDescriptors = Collections2.transform(annotations, annotation2descriptor);
        return annotationDescriptors.toArray(new AnnotationDescriptor[annotationDescriptors.size()]);
    }

    @Override
    public Map<String, AnnotationDescriptor> getAnnotation() {
        final AnnotationDescriptor[] annotations = getAnnotations();
        final Map<String, AnnotationDescriptor> result = Maps.newHashMap();
        for (AnnotationDescriptor annotation : annotations) {
            result.put(annotation.getName(), annotation);
        }
        return result;
    }

    @Override
    public String getName() {
        return this.method.getName();
    }

    @Override
    public TypeDescriptor getType() {
        return context.create(TypeDescriptor.class, method.getReturnType());
    }

    @Override
    public ParameterDescriptor[] getParameters() {
        final Parameter[] parameters = method.getParameters();
        return context.create(ParameterDescriptor[].class, parameters);
    }

    @Override
    public BeanMethodDescriptor getGetter() {
        if (isGetter()) {
            return this;
        }
        return context.create(BeanClassDescriptor.class, method.getDeclaringClass()).getGetter().get(getField().getName());
    }

    @Override
    public BeanMethodDescriptor getSetter() {
        if (isSetter()) {
            return this;
        }
        return context.create(BeanClassDescriptor.class, method.getDeclaringClass()).getSetter().get(getField().getName());
    }

    @Override
    public FieldDescriptor getField() {
        if (!isBean()) {
            return null;
        }
        return context.create(BeanClassDescriptor.class, method.getDeclaringClass()).getField().get(uncapitalize(getName().substring(3)));
    }

    @Override
    public boolean isGetter() {
        return getName().startsWith("get");
    }

    @Override
    public boolean isSetter() {
        return getName().startsWith("set");
    }

    @Override
    public boolean isBean() {
        return isGetter() || isSetter();
    }

    @Override
    public boolean isEntity() {
        return context.create(EntityTableDescriptor.class, this.method.getDeclaringClass()) != null;
    }

    @Override
    public ColumnDescriptor getColumn() {
        return context.create(ColumnDescriptor.class, this.method);
    }

    @Override
    public Help get_help() {
        return new HelpFactory().create(this);
    }

    @Override
    public Object get_root() {
        return method;
    }

    @Override
    public String toString() {
        return get_help().toString();
    }
}
