package org.simart.writeonce.common.builder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.simart.writeonce.application.Context;
import org.simart.writeonce.application.FlexibleGenerator;
import org.simart.writeonce.common.Action;
import org.simart.writeonce.common.ColumnNameResolver;
import org.simart.writeonce.common.ColumnTypeResolver;
import org.simart.writeonce.common.TableNameResolver;

public class JpaPlugin {

    public static void configure(FlexibleGenerator generator) {
	configure(generator, null, null, null);
    }

    @SuppressWarnings("rawtypes")
    public static void configure(FlexibleGenerator generator, ColumnNameResolver columnNameResolver, ColumnTypeResolver columnTypeResolver, TableNameResolver tableNameResolver) {
	ReflectionPlugin.configure(generator);

	generator.setHelper(columnNameResolver);
	generator.setHelper(columnTypeResolver);
	generator.setHelper(tableNameResolver);

	generator.getBuilder(Field.class).action("column", new Action<Field>() {
	    @Override
	    public Object execute(Field data, Context context) {
		return ColumnDescriptorBuilder.create().build(ColumnDescriptorBuilder.getColumn(data, context), context);
	    }
	});
	generator.getBuilder(Method.class).action("column", new Action<Method>() {
	    @Override
	    public Object execute(Method data, Context context) {
		return ColumnDescriptorBuilder.create().build(ColumnDescriptorBuilder.getColumn(data, context), context);
	    }
	});
	generator.getBuilder(Class.class).action("table", new Action<Class>() {
	    @Override
	    public Object execute(Class data, Context context) {
		return EntityDescriptorBuilder.create().build(EntityDescriptorBuilder.getTable(data), context);
	    }
	});
    }

}
