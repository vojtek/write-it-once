package org.simart.writeonce.common.builder;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.simart.writeonce.application.Context;
import org.simart.writeonce.common.Action;
import org.simart.writeonce.common.SourcePath;

@SuppressWarnings("rawtypes")
public class ClassDescriptorBuilder extends DefaultDescriptorBuilder<Class> {

    public static ClassDescriptorBuilder create() {
	final ClassDescriptorBuilder builder = new ClassDescriptorBuilder();

	builder.action("name", new Action<Class>() {
	    @Override
	    public Object execute(Class data, Context context) {
		return data.getName();
	    }
	});
	builder.action("shortName", new Action<Class>() {
	    @Override
	    public Object execute(Class data, Context context) {
		return data.getSimpleName();
	    }
	});
	builder.action("package", new Action<Class>() {
	    @Override
	    public Object execute(Class data, Context context) {
		return context.getBuilder(Package.class).build(data.getPackage(), context);
	    }
	});

	builder.action("methods", new Action<Class>() {
	    @Override
	    public Object execute(final Class data, Context context) {
		return DescriptorBuilders.build(context.getBuilder(Method.class), MethodDescriptorBuilder.getPublicMethods(data), context);
	    }
	});
	builder.action("method", new Action<Class>() {
	    @Override
	    public Object execute(final Class data, Context context) {
		return Descriptors.extract("name", DescriptorBuilders.build(context.getBuilder(Method.class), MethodDescriptorBuilder.getAllMethods(data), context));
	    }
	});
	builder.action("allMethods", new Action<Class>() {
	    @Override
	    public Object execute(final Class data, Context context) {
		return DescriptorBuilders.build(context.getBuilder(Method.class), MethodDescriptorBuilder.getAllMethods(data), context);
	    }
	});
	builder.action("getters", new Action<Class>() {
	    @Override
	    public Object execute(final Class data, Context context) {
		return DescriptorBuilders.build(context.getBuilder(Method.class), MethodDescriptorBuilder.getGetters(data), context);
	    }
	});
	builder.action("getter", new Action<Class>() {
	    @Override
	    public Object execute(final Class data, Context context) {
		return Descriptors.extract("property", DescriptorBuilders.build(context.getBuilder(Method.class), MethodDescriptorBuilder.getGetters(data), context));
	    }
	});
	builder.action("setters", new Action<Class>() {
	    @Override
	    public Object execute(final Class data, Context context) {
		return DescriptorBuilders.build(context.getBuilder(Method.class), MethodDescriptorBuilder.getSetters(data), context);
	    }
	});
	builder.action("setter", new Action<Class>() {
	    @Override
	    public Object execute(final Class data, Context context) {
		return Descriptors.extract("property", DescriptorBuilders.build(context.getBuilder(Method.class), MethodDescriptorBuilder.getSetters(data), context));
	    }
	});

	builder.action("fields", new Action<Class>() {
	    @Override
	    public Object execute(final Class data, Context context) {
		return DescriptorBuilders.build(context.getBuilder(Field.class), FieldDescriptorBuilder.getNonStaticFields(data), context);
	    }
	});
	builder.action("field", new Action<Class>() {
	    @Override
	    public Object execute(final Class data, Context context) {
		return Descriptors.extract("name", DescriptorBuilders.build(context.getBuilder(Field.class), FieldDescriptorBuilder.getAllFields(data), context));
	    }
	});
	builder.action("staticFields", new Action<Class>() {
	    @Override
	    public Object execute(final Class data, Context context) {
		return DescriptorBuilders.build(context.getBuilder(Field.class), FieldDescriptorBuilder.getStaticFields(data), context);
	    }
	});
	builder.action("staticField", new Action<Class>() {
	    @Override
	    public Object execute(final Class data, Context context) {
		return Descriptors.extract("name", DescriptorBuilders.build(context.getBuilder(Field.class), FieldDescriptorBuilder.getStaticFields(data), context));
	    }
	});

	builder.action("annotations", new Action<Class>() {
	    @Override
	    public Object execute(final Class data, Context context) {
		return DescriptorBuilders.build(context.getBuilder(Annotation.class), AnnotationDescriptorBuilder.getAllAnnotations(data), context);
	    }
	});
	builder.action("annotation", new Action<Class>() {
	    @Override
	    public Object execute(final Class data, Context context) {
		return Descriptors.extract("name",
			DescriptorBuilders.build(context.getBuilder(Annotation.class), AnnotationDescriptorBuilder.getAllAnnotations(data), context));
	    }
	});

	builder.action("isEnum", new Action<Class>() {
	    @Override
	    public Object execute(Class data, Context context) {
		return data.getEnumConstants() != null;
	    }
	});
	builder.action("enums", new Action<Class>() {
	    @Override
	    public Object execute(Class data, Context context) {
		return data.getEnumConstants();
	    }
	});

	builder.action("patch", new Action<Class>() {
	    @Override
	    public Object execute(Class data, Context context) {
		final String[] parts = data.getName().split("\\.");
		final StringBuilder result = new StringBuilder();
		result.append(context.getHelper(SourcePath.class).path);
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

}
