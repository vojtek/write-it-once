package org.simart.writeonce.common.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.reflections.ReflectionUtils;
import org.simart.writeonce.common.Action;
import org.simart.writeonce.common.DefaultDescriptorBuilder;
import org.simart.writeonce.common.DescriptorBuilder;

import com.google.common.collect.Lists;

public class AnnotationDescriptorBuilder extends DefaultDescriptorBuilder<Annotation> {

    private final static DescriptorBuilder<Class<?>> classDescriptorBuilder = ClassDescriptorBuilder.create();

    public static DescriptorBuilder<Annotation> create() {
        final AnnotationDescriptorBuilder builder = new AnnotationDescriptorBuilder();

        builder.action("type", new Action<Annotation>() {
            @Override
            public Object execute(Annotation data) {
                return classDescriptorBuilder.build(data.annotationType());
            }
        });

        builder.action("attribute", new Action<Annotation>() {
            @Override
            public Object execute(Annotation data) {
                return data;
            }
        });

        return builder;
    }

    @SuppressWarnings("unchecked")
    public static List<Annotation> getAllAnnotations(Class<?> type) {
        return Lists.newArrayList(ReflectionUtils.getAllAnnotations(type));
    }

    @SuppressWarnings("unchecked")
    public static List<Annotation> getAllAnnotations(Method type) {
        return Lists.newArrayList(ReflectionUtils.getAllAnnotations(type));
    }

    @SuppressWarnings("unchecked")
    public static List<Annotation> getAllAnnotations(Field type) {
        return Lists.newArrayList(ReflectionUtils.getAllAnnotations(type));
    }

}
