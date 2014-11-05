package org.simart.writeonce.common.builder;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.simart.writeonce.common.Action;

public class ClassDescriptorBuilder extends DefaultDescriptorBuilder<Class<?>> {

    public static ClassDescriptorBuilder create() {
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
                return builder.packageDescriptorBuilder.build(data.getPackage());
            }
        });

        builder.action("methods", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return DescriptorBuilders.build(builder.methodDescriptorBuilder, MethodDescriptorBuilder.getPublicMethods(data));
            }
        });
        builder.action("method", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return Descriptors.extract("name", DescriptorBuilders.build(builder.methodDescriptorBuilder, MethodDescriptorBuilder.getAllMethods(data)));
            }
        });
        builder.action("allMethods", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return DescriptorBuilders.build(builder.methodDescriptorBuilder, MethodDescriptorBuilder.getAllMethods(data));
            }
        });
        builder.action("getters", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return DescriptorBuilders.build(builder.methodDescriptorBuilder, MethodDescriptorBuilder.getGetters(data));
            }
        });
        builder.action("getter", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return Descriptors.extract("property", DescriptorBuilders.build(builder.methodDescriptorBuilder, MethodDescriptorBuilder.getGetters(data)));
            }
        });
        builder.action("setters", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return DescriptorBuilders.build(builder.methodDescriptorBuilder, MethodDescriptorBuilder.getSetters(data));
            }
        });
        builder.action("setter", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return Descriptors.extract("property", DescriptorBuilders.build(builder.methodDescriptorBuilder, MethodDescriptorBuilder.getSetters(data)));
            }
        });

        builder.action("fields", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return DescriptorBuilders.build(builder.fieldDescriptorBuilder, FieldDescriptorBuilder.getNonStaticFields(data));
            }
        });
        builder.action("staticFields", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return DescriptorBuilders.build(builder.fieldDescriptorBuilder, FieldDescriptorBuilder.getStaticFields(data));
            }
        });
        builder.action("field", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return Descriptors.extract("name", DescriptorBuilders.build(builder.fieldDescriptorBuilder, FieldDescriptorBuilder.getAllFields(data)));
            }
        });

        builder.action("annotations", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return DescriptorBuilders.build(builder.annotationDescriptorBuilder, AnnotationDescriptorBuilder.getAllAnnotations(data));
            }
        });
        builder.action("annotation", new Action<Class<?>>() {
            @Override
            public Object execute(final Class<?> data) {
                return Descriptors.extract("name", DescriptorBuilders.build(builder.annotationDescriptorBuilder, AnnotationDescriptorBuilder.getAllAnnotations(data)));
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

        builder.action("patch", new Action<Class<?>>() {
            @Override
            public Object execute(Class<?> data) {
                final String[] parts = data.getName().split("\\.");
                final StringBuilder result = new StringBuilder();
                result.append(builder.sourcePatch);
                for (int i = 0; i < parts.length; i++) {
                    result.append(parts[i]);
                    if (i + 1 != parts.length) {
                        result.append(File.separator);
                    }
                }
                result.append(".java");
                return result.toString();
            }
        });

        return builder;
    }

    DescriptorBuilder<Method> methodDescriptorBuilder = MethodDescriptorBuilder.create();
    DescriptorBuilder<Field> fieldDescriptorBuilder = FieldDescriptorBuilder.create();
    DescriptorBuilder<Annotation> annotationDescriptorBuilder = AnnotationDescriptorBuilder.create();
    DescriptorBuilder<Package> packageDescriptorBuilder = PackageDescriptorBuilder.create();
    private String sourcePatch = "src" + File.separator + "main" + File.separator + "java" + File.separator;

    public ClassDescriptorBuilder methodDescriptorBuilder(DescriptorBuilder<Method> methodDescriptorBuilder) {
        this.methodDescriptorBuilder = methodDescriptorBuilder;
        return this;
    }

    public ClassDescriptorBuilder fieldDescriptorBuilder(DescriptorBuilder<Field> fieldDescriptorBuilder) {
        this.fieldDescriptorBuilder = fieldDescriptorBuilder;
        return this;
    }

    public ClassDescriptorBuilder annotationDescriptorBuilder(DescriptorBuilder<Annotation> annotationDescriptorBuilder) {
        this.annotationDescriptorBuilder = annotationDescriptorBuilder;
        return this;
    }

    public ClassDescriptorBuilder packageDescriptorBuilder(DescriptorBuilder<Package> packageDescriptorBuilder) {
        this.packageDescriptorBuilder = packageDescriptorBuilder;
        return this;
    }

    public ClassDescriptorBuilder sourcePatch(String sourcePatch) {
        this.sourcePatch = sourcePatch;
        return this;
    }

    public DescriptorBuilder<Method> getMethodDescriptorBuilder() {
        return methodDescriptorBuilder;
    }

    public DescriptorBuilder<Field> getFieldDescriptorBuilder() {
        return fieldDescriptorBuilder;
    }

    public DescriptorBuilder<Annotation> getAnnotationDescriptorBuilder() {
        return annotationDescriptorBuilder;
    }

    public DescriptorBuilder<Package> getPackageDescriptorBuilder() {
        return packageDescriptorBuilder;
    }

}
