package org.simart.writeonce.common.builder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.simart.writeonce.application.Context;
import org.simart.writeonce.common.Action;
import org.simart.writeonce.common.ColumnNameResolver;
import org.simart.writeonce.common.ColumnTypeResolver;
import org.simart.writeonce.common.builder.ColumnDescriptorBuilder.Column;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class ColumnDescriptorBuilder extends DefaultDescriptorBuilder<Column> {

    public static final ColumnDescriptorBuilder create() {
	final ColumnDescriptorBuilder builder = new ColumnDescriptorBuilder();

	builder.action("name", new Action<Column>() {
	    @Override
	    public Object execute(Column data, Context context) {
		if (data.column != null && !data.column.name().isEmpty()) {
		    return data.column.name();
		}
		return context.getHelper(ColumnNameResolver.class).getName(data.field.getName());
	    }
	});

	builder.action("type", new Action<Column>() {
	    @Override
	    public Object execute(Column data, Context context) {
		return context.getHelper(ColumnTypeResolver.class).getType(data.column, data.field.getType(), data.field);
	    }
	});
	builder.action("fullType", new Action<Column>() {
	    @Override
	    public Object execute(Column data, Context context) {
		return context.getHelper(ColumnTypeResolver.class).getFullType(data.column, data.field.getType(), data.field);
	    }
	});

	builder.action("length", new Action<Column>() {
	    @Override
	    public Object execute(Column data, Context context) {
		return data.column == null ? 0 : data.column.length();
	    }
	});
	builder.action("nullable", new Action<Column>() {
	    @Override
	    public Object execute(Column data, Context context) {
		return data.column == null ? false : data.column.nullable();
	    }
	});
	builder.action("scale", new Action<Column>() {
	    @Override
	    public Object execute(Column data, Context context) {
		return data.column == null ? 0 : data.column.scale();
	    }
	});
	builder.action("precision", new Action<Column>() {
	    @Override
	    public Object execute(Column data, Context context) {
		return data.column == null ? 0 : data.column.precision();
	    }
	});

	return builder;
    }

    public static List<Column> getColumns(Class<?> cls, Context context) {
	final List<Column> columns = Lists.newArrayList();
	for (Field field : cls.getDeclaredFields()) {
	    columns.add(getColumn(field, context));
	}
	return columns;
    }

    public static Column getColumn(Field data, Context context) {
	javax.persistence.Column column = data.getAnnotation(javax.persistence.Column.class);
	if (column != null) {
	    return new Column(data.getAnnotation(javax.persistence.Column.class), data);
	}
	@SuppressWarnings("unchecked")
	final Descriptor<Method> methodDescriptor = (Descriptor<Method>) FieldDescriptorBuilder.ACTION_GETTER.execute(data, context);
	return getColumn(methodDescriptor.getData(), context);
    }

    public static Column getColumn(Method data, Context context) {
	javax.persistence.Column column = data.getAnnotation(javax.persistence.Column.class);
	@SuppressWarnings("unchecked")
	final Descriptor<Field> fieldDescriptor = (Descriptor<Field>) MethodDescriptorBuilder.ACTION_PROPERTY.execute(data, context);
	Preconditions.checkNotNull(fieldDescriptor);
	final Field field = fieldDescriptor.getData();
	if (column == null) {
	    column = field.getAnnotation(javax.persistence.Column.class);
	}
	return new Column(column, field);
    }

    public static class Column {
	private final javax.persistence.Column column;
	private final Field field;

	public Column(javax.persistence.Column column, Field field) {
	    super();
	    this.column = column;
	    this.field = field;
	}

    }

}
