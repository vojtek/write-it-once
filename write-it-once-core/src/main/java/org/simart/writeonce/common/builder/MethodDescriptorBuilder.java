package org.simart.writeonce.common.builder;

import static org.reflections.ReflectionUtils.withModifier;
import static org.reflections.ReflectionUtils.withPrefix;
import static org.simart.writeonce.utils.StringUtils.uncapitalize;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.reflections.ReflectionUtils;
import org.simart.writeonce.common.Action;
import org.simart.writeonce.common.builder.MethodParameterDescriptorBuilder.MethodParameter;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class MethodDescriptorBuilder extends DefaultDescriptorBuilder<Method> {

    private final static DescriptorBuilder<Class<?>> classDescriptorBuilder = ClassDescriptorBuilder.create();
    private static final DescriptorBuilder<Field> fieldDescriptorBuilder = FieldDescriptorBuilder.create();
    private static final DescriptorBuilder<Annotation> annotationDescriptorBuilder = AnnotationDescriptorBuilder.create();
    private static final DescriptorBuilder<MethodParameter> methodParameterDescriptorBuilder = MethodParameterDescriptorBuilder.create();

    public static MethodDescriptorBuilder create() {
        final MethodDescriptorBuilder builder = new MethodDescriptorBuilder();

        builder.action("name", new Action<Method>() {
            @Override
            public Object execute(Method data) {
                return data.getName();
            }
        });

        builder.action("type", new Action<Method>() {
            @Override
            public Object execute(Method data) {
                return classDescriptorBuilder.build(data.getReturnType());
            }
        });

        builder.action("owner", new Action<Method>() {
            @Override
            public Object execute(Method data) {
                return classDescriptorBuilder.build(data.getDeclaringClass());
            }
        });

        builder.action("annotations", new Action<Method>() {
            @Override
            public Object execute(final Method data) {
                return DescriptorBuilders.build(annotationDescriptorBuilder, AnnotationDescriptorBuilder.getAllAnnotations(data));
            }
        });
        builder.action("annotation", new Action<Method>() {
            @Override
            public Object execute(final Method data) {
                return Descriptors.extract("name", DescriptorBuilders.build(annotationDescriptorBuilder, AnnotationDescriptorBuilder.getAllAnnotations(data)));
            }
        });

        builder.action("parameters", new Action<Method>() {
            @Override
            public Object execute(final Method data) {
                return DescriptorBuilders.build(methodParameterDescriptorBuilder, MethodParameterDescriptorBuilder.getAllMethodParameters(data));
            }
        });
        builder.action("parameter", new Action<Method>() {
            @Override
            public Object execute(final Method data) {
                return Descriptors.extract("index", DescriptorBuilders.build(annotationDescriptorBuilder, AnnotationDescriptorBuilder.getAllAnnotations(data)));
            }
        });

        builder.action("property", ACTION_PROPERTY);

        // bean methods
        //builder.action("fieldName", null); // todo

        return builder;
    }

    public static final Action<Method> ACTION_PROPERTY = new Action<Method>() {
        @Override
        public Object execute(Method data) {
            final String name = data.getName();
            if (name.length() < 4 || !name.matches("(get)|(set)[A-Z0-9].*")) {
                return null;
            }
            final String propertyName = uncapitalize(name.substring(3));
            try {
                final Field field = data.getDeclaringClass().getDeclaredField(propertyName);
                return fieldDescriptorBuilder.build(field);
            } catch (NoSuchFieldException ex) {
                return null;
            }
        }
    };

    @SuppressWarnings("unchecked")
    public static List<Method> getAllMethods(Class<?> type) {
        return Lists.newArrayList(ReflectionUtils.getAllMethods(type));
    }

    @SuppressWarnings("unchecked")
    public static List<Method> getPublicMethods(Class<?> type) {
        return Lists.newArrayList(ReflectionUtils.getAllMethods(type, withModifier(Modifier.PUBLIC)));
    }

    @SuppressWarnings("unchecked")
    public static List<Method> getGetters(Class<?> type) {
        return Lists.newArrayList(ReflectionUtils.getAllMethods(type, withPrefix("get"), withModifier(Modifier.PUBLIC)));
    }

    @SuppressWarnings("unchecked")
    public static List<Method> getSetters(Class<?> type) {
        return Lists.newArrayList(ReflectionUtils.getAllMethods(type, withPrefix("set"), withModifier(Modifier.PUBLIC)));
    }

    public static List<MethodDescriptorBuilder> describe(List<Method> methods, Function<Method, MethodDescriptorBuilder> transformer) {
        return Lists.transform(methods, transformer);
    }

}
