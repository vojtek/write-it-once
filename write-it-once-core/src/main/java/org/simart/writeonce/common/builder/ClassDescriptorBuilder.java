package org.simart.writeonce.common.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.simart.writeonce.common.Action;
import org.simart.writeonce.common.DefaultDescriptorBuilder;
import org.simart.writeonce.common.DescriptorBuilder;
import org.simart.writeonce.common.Descriptors;

public class ClassDescriptorBuilder extends DefaultDescriptorBuilder<Class<?>> {

    private static final DescriptorBuilder<Method> methodDescriptorBuilder = MethodDescriptorBuilder.create();
    private static final DescriptorBuilder<Field> fieldDescriptorBuilder = FieldDescriptorBuilder.create();
    private static final DescriptorBuilder<Annotation> annotationDescriptorBuilder = AnnotationDescriptorBuilder.create();
    private static final DescriptorBuilder<Package> packageDescriptorBuilder = PackageDescriptorBuilder.create();

    public static DescriptorBuilder<Class<?>> create() {
        final ClassDescriptorBuilder builder = new ClassDescriptorBuilder();

        builder.action("name", new Action<Class<?>>() {
            @Override
            public Object execute(Class<?> data) {
                return data.getName();
            }
        });
        builder.action("shortName", new Action<Class<?>>() {
            @Override
            public Object execute(Class<?> data) {
                return data.getSimpleName();
            }
        });
        builder.action("package", new Action<Class<?>>() {

            @Override
            public Object execute(Class<?> data) {
                return packageDescriptorBuilder.build(data.getPackage());
            }
        });

        builder.action("methods", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return DescriptorBuilders.build(methodDescriptorBuilder, MethodDescriptorBuilder.getAllMethods(data));
            }
        });
        builder.action("method", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return Descriptors.extract("name", DescriptorBuilders.build(methodDescriptorBuilder, MethodDescriptorBuilder.getAllMethods(data)));
            }
        });
        builder.action("getters", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return DescriptorBuilders.build(methodDescriptorBuilder, MethodDescriptorBuilder.getGetters(data));
            }
        });
        builder.action("getter", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return Descriptors.extract("property", DescriptorBuilders.build(methodDescriptorBuilder, MethodDescriptorBuilder.getGetters(data)));
            }
        });
        builder.action("setters", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return DescriptorBuilders.build(methodDescriptorBuilder, MethodDescriptorBuilder.getSetters(data));
            }
        });
        builder.action("setter", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return Descriptors.extract("property", DescriptorBuilders.build(methodDescriptorBuilder, MethodDescriptorBuilder.getSetters(data)));
            }
        });

        builder.action("fields", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return DescriptorBuilders.build(fieldDescriptorBuilder, FieldDescriptorBuilder.getAllFields(data));
            }
        });
        builder.action("field", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return Descriptors.extract("name", DescriptorBuilders.build(fieldDescriptorBuilder, FieldDescriptorBuilder.getAllFields(data)));
            }
        });

        builder.action("annotations", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return DescriptorBuilders.build(annotationDescriptorBuilder, AnnotationDescriptorBuilder.getAllAnnotations(data));
            }
        });
        builder.action("annotation", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return Descriptors.extract("name", DescriptorBuilders.build(annotationDescriptorBuilder, AnnotationDescriptorBuilder.getAllAnnotations(data)));
            }
        });

        builder.action("isEnum", new Action<Class<?>>() {
            @Override
            public Object execute(Class<?> data) {
                return data.getEnumConstants() != null;
            }
        });
        builder.action("enums", new Action<Class<?>>() {
            @Override
            public Object execute(Class<?> data) {
                return data.getEnumConstants();
            }
        });

        return builder;
    }
}
