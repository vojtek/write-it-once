package org.simart.writeonce.common.builder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

import javax.persistence.Id;

import org.reflections.ReflectionUtils;
import org.simart.writeonce.application.Context;
import org.simart.writeonce.common.Action;
import org.simart.writeonce.common.TableNameResolver;
import org.simart.writeonce.common.builder.ColumnDescriptorBuilder.Column;
import org.simart.writeonce.common.builder.EntityDescriptorBuilder.Table;

public class EntityDescriptorBuilder extends DefaultDescriptorBuilder<Table> {

    public static EntityDescriptorBuilder create() {
	final EntityDescriptorBuilder builder = new EntityDescriptorBuilder();

	builder.action("schema", new Action<Table>() {
	    @Override
	    public Object execute(Table data, Context context) {
		return data.getSchema();
	    }

	});
	builder.action("name", new Action<Table>() {
	    @Override
	    public Object execute(Table data, Context context) {
		if (data.table == null || data.table.name() == null) {
		    return context.getHelper(TableNameResolver.class).getName(data.entity);
		}
		return data.table.name();
	    }
	});
	builder.action("columns", new Action<Table>() {
	    @Override
	    public Object execute(Table data, Context context) {
		return DescriptorBuilders.build(context.getBuilder(Column.class), ColumnDescriptorBuilder.getColumns(data.entity, context), context);
	    }
	});
	builder.action("column", new Action<Table>() {
	    @Override
	    public Object execute(Table data, Context context) {
		return Descriptors.extract("name", DescriptorBuilders.build(context.getBuilder(Column.class), ColumnDescriptorBuilder.getColumns(data.entity, context), context));
	    }
	});

	builder.action("primaryKey", new Action<Table>() {
	    @Override
	    public Object execute(Table data, Context context) {
		@SuppressWarnings("unchecked")
		final Set<Field> fields = ReflectionUtils.getFields(data.entity, ReflectionUtils.withAnnotation(Id.class));
		if (fields.size() == 1) {
		    return context.getBuilder(Column.class).build(ColumnDescriptorBuilder.getColumn(fields.iterator().next(), context), context);
		}
		@SuppressWarnings("unchecked")
		final Set<Method> methods = ReflectionUtils.getMethods(data.entity, ReflectionUtils.withAnnotation(Id.class));
		if (methods.size() == 1) {
		    return context.getBuilder(Column.class).build(ColumnDescriptorBuilder.getColumn(methods.iterator().next(), context), context);
		}
		return null;
	    }
	});

	builder.action("entity", new Action<Table>() {
	    @Override
	    public Object execute(Table data, Context context) {
		return context.getBuilder(Class.class).build(data.entity, context);
	    }
	});

	return builder;
    }

    public static Table getTable(Class<?> data) {
	return new Table(data.getAnnotation(javax.persistence.Table.class), data);
    }

    public static class Table {
	private final javax.persistence.Table table;
	private final Class<?> entity;

	public Table(javax.persistence.Table table, Class<?> entity) {
	    super();
	    this.table = table;
	    this.entity = entity;
	}

	private String getSchema() {
	    return table == null ? null : table.schema();
	}

    }

}
