package org.simart.writeonce.common.builder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.simart.writeonce.common.Action;

public class JpaDescriptorConfigurator {

    public static void configureFieldDescriptor(DescriptorBuilder<Field> fieldDescriptorBuilder) {
        fieldDescriptorBuilder.action("column", new Action<Field>() {
            @Override
            public Object execute(Field data) {
                return ColumnDescriptorBuilder.create().build(ColumnDescriptorBuilder.getColumn(data));
            }
        });
    }

    public static void configureMethodDescriptor(DescriptorBuilder<Method> methodDescriptorBuilder) {
        methodDescriptorBuilder.action("column", new Action<Method>() {
            @Override
            public Object execute(Method data) {
                return ColumnDescriptorBuilder.create().build(ColumnDescriptorBuilder.getColumn(data));
            }
        });
    }

    public static void configure(ClassDescriptorBuilder classDescriptorBuilder) {
        classDescriptorBuilder.action("table", new Action<Class<?>>() {
            @Override
            public Object execute(Class<?> data) {
                return EntityDescriptorBuilder.create().build(EntityDescriptorBuilder.getTable(data));
            }
        });
        configureFieldDescriptor(classDescriptorBuilder.getFieldDescriptorBuilder());
        configureMethodDescriptor(classDescriptorBuilder.getMethodDescriptorBuilder());
    }

    public static ClassDescriptorBuilder createClassDescriptorBuilder() {
        final ClassDescriptorBuilder builder = ClassDescriptorBuilder.create();
        configure(builder);
        return builder;
    }
}
