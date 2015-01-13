package org.simart.writeonce.common.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.persistence.Id;

import org.reflections.ReflectionUtils;
import org.simart.writeonce.application.Context;
import org.simart.writeonce.common.Action;
import org.simart.writeonce.common.TableNameResolver;
import org.simart.writeonce.common.builder.ColumnDescriptorBuilder.Column;
import org.simart.writeonce.common.builder.EntityDescriptorBuilder.Entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class EntityDescriptorBuilder extends DefaultDescriptorBuilder<Entity> {

    public static EntityDescriptorBuilder create() {
	final EntityDescriptorBuilder builder = new EntityDescriptorBuilder();

	builder.action("schema", new Action<Entity>() {
	    @Override
	    public Object execute(Entity data, Context context) {
		return data.getSchema();
	    }

	});
	builder.action("name", new Action<Entity>() {
	    @Override
	    public Object execute(Entity data, Context context) {
		if (data.getTable() == null || data.getTable().name() == null) {
		    return context.getHelper(TableNameResolver.class).getName(data.getType());
		}
		return data.getTable().name();
	    }
	});
	builder.action("columns", new Action<Entity>() {
	    @Override
	    public Object execute(Entity data, Context context) {
		return DescriptorBuilders.build(context.getBuilder(Column.class), ColumnDescriptorBuilder.getColumns(data.getType(), context), context);
	    }
	});
	builder.action("column", new Action<Entity>() {
	    @Override
	    public Object execute(Entity data, Context context) {
		return Descriptors.extract("name",
			DescriptorBuilders.build(context.getBuilder(Column.class), ColumnDescriptorBuilder.getColumns(data.getType(), context), context));
	    }
	});
	builder.action("annotations", new Action<Entity>() {
	    @Override
	    public Object execute(final Entity data, Context context) {
		return DescriptorBuilders.build(context.getBuilder(Annotation.class), Lists.newArrayList(data.getAnnotations()), context);
	    }
	});
	builder.action("annotation", new Action<Entity>() {
	    @Override
	    public Object execute(final Entity data, Context context) {
		return Descriptors.extract("name",
			DescriptorBuilders.build(context.getBuilder(Annotation.class), Lists.newArrayList(data.getAnnotations()), context));
	    }
	});

	builder.action("primaryKey", new Action<Entity>() {
	    @Override
	    public Object execute(Entity data, Context context) {
		@SuppressWarnings("unchecked")
		final Set<Field> fields = ReflectionUtils.getFields(data.getType(), ReflectionUtils.withAnnotation(Id.class));
		if (fields.size() == 1) {
		    return context.getBuilder(Column.class).build(ColumnDescriptorBuilder.getColumn(fields.iterator().next(), context), context);
		}
		@SuppressWarnings("unchecked")
		final Set<Method> methods = ReflectionUtils.getMethods(data.getType(), ReflectionUtils.withAnnotation(Id.class));
		if (methods.size() == 1) {
		    return context.getBuilder(Column.class).build(ColumnDescriptorBuilder.getColumn(methods.iterator().next(), context), context);
		}
		return null;
	    }
	});

	builder.action("entity", new Action<Entity>() {
	    @Override
	    public Object execute(Entity data, Context context) {
		return context.getBuilder(Class.class).build(data.getType(), context);
	    }
	});

	return builder;
    }

    public static Entity getEntity(Class<?> data) {
	return new Entity(Arrays.asList(data.getAnnotations()), data);
    }

    public static class Entity {
	private final Map<String, Annotation> annotations = Maps.newHashMap();
	private final Class<?> type;

	public Entity(Collection<Annotation> annotations, Class<?> type) {
	    super();
	    for (Annotation annotation : annotations) {
		this.annotations.put(annotation.annotationType().getName(), annotation);
	    }
	    this.type = type;
	}

	private String getSchema() {
	    final javax.persistence.Table table = getTable();
	    return table == null ? null : table.schema();
	}

	public javax.persistence.Table getTable() {
	    return getAnnotation(javax.persistence.Table.class);
	}

	public Class<?> getType() {
	    return type;
	}

	@SuppressWarnings("unchecked")
	public <E> E getAnnotation(Class<E> annotationType) {
	    return (E) annotations.get(annotationType.getName());
	}

	public Collection<Annotation> getAnnotations() {
	    return annotations.values();
	}

    }

}
