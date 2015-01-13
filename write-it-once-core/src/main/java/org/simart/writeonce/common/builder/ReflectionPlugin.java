package org.simart.writeonce.common.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.simart.writeonce.application.Generator;

public class ReflectionPlugin {

    public static void configure(Generator generator) {
	generator.register(Class.class, ClassDescriptorBuilder.create());
	generator.register(Field.class, FieldDescriptorBuilder.create());
	generator.register(Method.class, MethodDescriptorBuilder.create());
	generator.register(Annotation.class, AnnotationDescriptorBuilder.create());
	generator.register(Package.class, PackageDescriptorBuilder.create());
    }
}
