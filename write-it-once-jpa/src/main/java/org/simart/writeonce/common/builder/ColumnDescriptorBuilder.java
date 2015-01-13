package org.simart.writeonce.common.builder;

import static org.reflections.ReflectionUtils.withModifier;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.reflections.ReflectionUtils;
import org.simart.writeonce.application.Context;
import org.simart.writeonce.common.Action;
import org.simart.writeonce.common.ColumnNameResolver;
import org.simart.writeonce.common.ColumnTypeResolver;
import org.simart.writeonce.common.ColumnTypeResolver.TypeDescriptor;
import org.simart.writeonce.common.builder.ColumnDescriptorBuilder.Column;
import org.testng.collections.Maps;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class ColumnDescriptorBuilder extends DefaultDescriptorBuilder<Column> {

    public static final ColumnDescriptorBuilder create() {
	final ColumnDescriptorBuilder builder = new ColumnDescriptorBuilder();

	builder.action("name", new Action<Column>() {
	    @Override
	    public Object execute(Column data, Context context) {
		final javax.persistence.Column column = data.getAnnotation(javax.persistence.Column.class);
		if (column != null && !column.name().isEmpty()) {
		    return column.name();
		}
		return context.getHelper(ColumnNameResolver.class).getName(data.getField().getName());
	    }
	});

	builder.action("type", new Action<Column>() {
	    @Override
	    public Object execute(Column data, Context context) {
		final javax.persistence.Column column = data.getAnnotation(javax.persistence.Column.class);
		return context.getHelper(ColumnTypeResolver.class).getType(new TypeDescriptor(column, data.getField().getType(), data.getField(), data.getAnnotations()));
	    }
	});
	builder.action("fullType", new Action<Column>() {
	    @Override
	    public Object execute(Column data, Context context) {
		final javax.persistence.Column column = data.getAnnotation(javax.persistence.Column.class);
		return context.getHelper(ColumnTypeResolver.class).getFullType(new TypeDescriptor(column, data.getField().getType(), data.getField(), data.getAnnotations()));
	    }
	});

	builder.action("length", new Action<Column>() {
	    @Override
	    public Object execute(Column data, Context context) {
		final javax.persistence.Column column = data.getAnnotation(javax.persistence.Column.class);
		return column == null ? 0 : column.length();
	    }
	});
	builder.action("nullable", new Action<Column>() {
	    @Override
	    public Object execute(Column data, Context context) {
		final javax.persistence.Column column = data.getAnnotation(javax.persistence.Column.class);
		return column == null ? 0 : column.nullable();
	    }
	});
	builder.action("scale", new Action<Column>() {
	    @Override
	    public Object execute(Column data, Context context) {
		final javax.persistence.Column column = data.getAnnotation(javax.persistence.Column.class);
		return column == null ? 0 : column.scale();
	    }
	});
	builder.action("precision", new Action<Column>() {
	    @Override
	    public Object execute(Column data, Context context) {
		final javax.persistence.Column column = data.getAnnotation(javax.persistence.Column.class);
		return column == null ? 0 : column.precision();
	    }
	});
	builder.action("annotations", new Action<Column>() {
	    @Override
	    public Object execute(final Column data, Context context) {
		return DescriptorBuilders.build(context.getBuilder(Annotation.class), Lists.newArrayList(data.getAnnotations()), context);
	    }
	});
	builder.action("annotation", new Action<Column>() {
	    @Override
	    public Object execute(final Column data, Context context) {
		return Descriptors.extract("name",
			DescriptorBuilders.build(context.getBuilder(Annotation.class), Lists.newArrayList(data.getAnnotations()), context));
	    }
	});

	return builder;
    }

    @SuppressWarnings("unchecked")
    public static List<Column> getColumns(Class<?> cls, Context context) {
	final List<Column> columns = Lists.newArrayList();
	for (Field field : Sets.difference(ReflectionUtils.getAllFields(cls), ReflectionUtils.getAllFields(cls, withModifier(Modifier.STATIC)))) {
	    columns.add(getColumn(field, context));
	}
	return columns;
    }

    public static Column getColumn(Field data, Context context) {
	final Collection<Annotation> annotations = Lists.newArrayList();
	annotations.addAll(Arrays.asList(data.getAnnotations()));
	@SuppressWarnings("unchecked")
	final Descriptor<Method> methodDescriptor = (Descriptor<Method>) FieldDescriptorBuilder.ACTION_GETTER.execute(data, context);
	if (methodDescriptor != null) {
	    annotations.addAll(Arrays.asList(methodDescriptor.getData().getAnnotations()));
	}
	return new Column(annotations, data);
    }

    public static Column getColumn(Method data, Context context) {
	@SuppressWarnings("unchecked")
	final Descriptor<Field> fieldDescriptor = (Descriptor<Field>) MethodDescriptorBuilder.ACTION_PROPERTY.execute(data, context);
	if (fieldDescriptor == null) {
	    throw new RuntimeException("unable to find property for method: " + data.getName());
	}
	return getColumn(fieldDescriptor.getData(), context);
    }

    public static class Column {
	private final Map<String, Annotation> annotations = Maps.newHashMap();
	private final Field field;

	public Column(Collection<Annotation> annotations, Field field) {
	    super();
	    for (Annotation annotation : annotations) {
		this.annotations.put(annotation.annotationType().getName(), annotation);
	    }
	    this.field = field;
	}

	@SuppressWarnings("unchecked")
	public <E> E getAnnotation(Class<E> annotationType) {
	    return (E) annotations.get(annotationType.getName());
	}

	public Field getField() {
	    return field;
	}

	public Collection<Annotation> getAnnotations() {
	    return annotations.values();
	}

    }

}
