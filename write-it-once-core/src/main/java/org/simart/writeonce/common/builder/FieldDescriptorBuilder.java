package org.simart.writeonce.common.builder;

import static org.reflections.ReflectionUtils.withModifier;
import static org.simart.writeonce.utils.StringUtils.capitalize;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;

import org.reflections.ReflectionUtils;
import org.simart.writeonce.application.Context;
import org.simart.writeonce.common.Action;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class FieldDescriptorBuilder extends DefaultDescriptorBuilder<Field> {

    public static FieldDescriptorBuilder create() {
	final FieldDescriptorBuilder builder = new FieldDescriptorBuilder();

	builder.action("name", new Action<Field>() {
	    @Override
	    public Object execute(Field data, Context context) {
		return data.getName();
	    }
	});

	builder.action("type", new Action<Field>() {
	    @Override
	    public Object execute(Field data, Context context) {
		return context.getBuilder(Class.class).build(data.getType(), context);
	    }
	});

	builder.action("owner", new Action<Field>() {
	    @Override
	    public Object execute(Field data, Context context) {
		return context.getBuilder(Class.class).build(data.getDeclaringClass(), context);
	    }
	});

	builder.action("annotations", new Action<Field>() {
	    @Override
	    public Object execute(final Field data, Context context) {
		return DescriptorBuilders.build(context.getBuilder(Annotation.class), AnnotationDescriptorBuilder.getAllAnnotations(data), context);
	    }
	});
	builder.action("annotation", new Action<Field>() {
	    @Override
	    public Object execute(final Field data, Context context) {
		return Descriptors.extract("name",
			DescriptorBuilders.build(context.getBuilder(Annotation.class), AnnotationDescriptorBuilder.getAllAnnotations(data), context));
	    }
	});
	// only static fields
	builder.action("value", new Action<Field>() {
	    @Override
	    public Object execute(Field data, Context context) {
		try {
		    data.setAccessible(true);
		    return data.get(null);
		} catch (IllegalArgumentException e) {
		    throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
		    throw new RuntimeException(e);
		}
	    }
	});

	builder.action("getter", ACTION_GETTER);
	builder.action("setter", ACTION_SETTER);

	return builder;
    }

    public static final Action<Field> ACTION_GETTER = new Action<Field>() {
	@Override
	public Object execute(Field data, Context context) {
	    final String name = data.getName();
	    try {
		final Method method = data.getDeclaringClass().getMethod("get" + capitalize(name));
		return context.getBuilder(Method.class).build(method, context);
	    } catch (NoSuchMethodException e) {
		return null;
	    } catch (SecurityException e) {
		return null;
	    }
	}
    };
    public static final Action<Field> ACTION_SETTER = new Action<Field>() {
	@Override
	public Object execute(Field data, Context context) {
	    final String name = data.getName();
	    try {
		final Method method = data.getDeclaringClass().getMethod("set" + capitalize(name), data.getType());
		return context.getBuilder(Method.class).build(method, context);
	    } catch (NoSuchMethodException e) {
		return null;
	    } catch (SecurityException e) {
		return null;
	    }
	}
    };

    @SuppressWarnings("unchecked")
    public static List<Field> getAllFields(Class<?> type) {
	return Lists.newArrayList(ReflectionUtils.getAllFields(type));
    }

    @SuppressWarnings("unchecked")
    public static List<Field> getNonStaticFields(Class<?> type) {
	final Set<Field> fields = Sets.difference(ReflectionUtils.getAllFields(type), ReflectionUtils.getAllFields(type, withModifier(Modifier.STATIC)));
	return Lists.newArrayList(fields);
    }

    @SuppressWarnings("unchecked")
    public static List<Field> getStaticFields(Class<?> type) {
	return Lists.newArrayList(ReflectionUtils.getAllFields(type, withModifier(Modifier.STATIC)));
    }

}
