package org.simart.writeonce.common.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.simart.writeonce.application.FlexibleGenerator;

public class ReflectionPlugin {

    public static void configure(FlexibleGenerator generator) {
	generator.registerBuilder(Class.class, ClassDescriptorBuilder.create());
	generator.registerBuilder(Field.class, FieldDescriptorBuilder.create());
	generator.registerBuilder(Method.class, MethodDescriptorBuilder.create());
	generator.registerBuilder(Annotation.class, AnnotationDescriptorBuilder.create());
	generator.registerBuilder(Package.class, PackageDescriptorBuilder.create());
    }
}
