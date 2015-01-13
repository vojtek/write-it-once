package org.simart.writeonce.common.builder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.simart.writeonce.application.Context;
import org.simart.writeonce.application.Generator;
import org.simart.writeonce.common.Action;
import org.simart.writeonce.common.ColumnNameResolver;
import org.simart.writeonce.common.ColumnTypeResolver;
import org.simart.writeonce.common.TableNameResolver;
import org.simart.writeonce.common.builder.ColumnDescriptorBuilder.Column;
import org.simart.writeonce.common.builder.EntityDescriptorBuilder.Entity;

public class JpaPlugin {

    public static void configure(Generator generator) {
	configure(generator, null, null, null);
    }

    @SuppressWarnings("rawtypes")
    public static void configure(Generator generator, ColumnNameResolver columnNameResolver, ColumnTypeResolver columnTypeResolver, TableNameResolver tableNameResolver) {
	ReflectionPlugin.configure(generator);

	generator.register(Entity.class, EntityDescriptorBuilder.create());
	generator.register(Column.class, ColumnDescriptorBuilder.create());

	generator.setHelper(ColumnNameResolver.class, columnNameResolver);
	generator.setHelper(ColumnTypeResolver.class, columnTypeResolver);
	generator.setHelper(TableNameResolver.class, tableNameResolver);

	generator.getBuilder(Field.class).action("column", new Action<Field>() {
	    @Override
	    public Object execute(Field data, Context context) {
		return context.getBuilder(Column.class).build(ColumnDescriptorBuilder.getColumn(data, context), context);
	    }
	});
	generator.getBuilder(Method.class).action("column", new Action<Method>() {
	    @Override
	    public Object execute(Method data, Context context) {
		return context.getBuilder(Column.class).build(ColumnDescriptorBuilder.getColumn(data, context), context);
	    }
	});
	generator.getBuilder(Class.class).action("table", new Action<Class>() {
	    @Override
	    public Object execute(Class data, Context context) {
		return context.getBuilder(Entity.class).build(EntityDescriptorBuilder.getEntity(data), context);
	    }
	});
	generator.getBuilder(Class.class).action("columns", new Action<Class>() {
	    @Override
	    public Object execute(Class data, Context context) {
		return DescriptorBuilders.build(context.getBuilder(Column.class), ColumnDescriptorBuilder.getColumns(data, context), context);
	    }
	});
	generator.getBuilder(Class.class).action("column", new Action<Class>() {
	    @Override
	    public Object execute(Class data, Context context) {
		return Descriptors.extract("name",
			DescriptorBuilders.build(context.getBuilder(Column.class), ColumnDescriptorBuilder.getColumns(data, context), context));
	    }
	});
    }

}
