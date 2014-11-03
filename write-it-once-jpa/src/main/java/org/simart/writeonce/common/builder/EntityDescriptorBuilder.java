package org.simart.writeonce.common.builder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

import javax.persistence.Id;

import org.reflections.ReflectionUtils;
import org.simart.writeonce.common.Action;
import org.simart.writeonce.common.TableNameResolver;
import org.simart.writeonce.common.builder.EntityDescriptorBuilder.Table;

public class EntityDescriptorBuilder extends DefaultDescriptorBuilder<Table> {

    private static final DescriptorBuilder<org.simart.writeonce.common.builder.ColumnDescriptorBuilder.Column> columnDescriptorBuilder = ColumnDescriptorBuilder.create();
    private final static DescriptorBuilder<Class<?>> classDescriptorBuilder = ClassDescriptorBuilder.create();

    public static EntityDescriptorBuilder create() {
        final EntityDescriptorBuilder builder = EntityDescriptorBuilder.create();

        builder.action("schema", new Action<Table>() {
            @Override
            public Object execute(Table data) {
                return data.getSchema();
            }
        });
        builder.action("name", new EntityAction() {
            @Override
            public Object execute(Table data, TableNameResolver tableNameResolver) {
                if (data.table == null || data.table.name() == null) {
                    return tableNameResolver.getName(data.entity);
                }
                return data.table.name();
            }
        });
        builder.action("columns", new Action<Table>() {

            @Override
            public Object execute(Table data) {
                return DescriptorBuilders.build(columnDescriptorBuilder, ColumnDescriptorBuilder.getColumns(data.entity));
            }
        });
        builder.action("column", new Action<Table>() {
            @Override
            public Object execute(Table data) {
                return Descriptors.extract("name", DescriptorBuilders.build(columnDescriptorBuilder, ColumnDescriptorBuilder.getColumns(data.entity)));
            }
        });

        builder.action("primaryKey", new Action<Table>() {
            @Override
            public Object execute(Table data) {
                @SuppressWarnings("unchecked")
                final Set<Field> fields = ReflectionUtils.getFields(data.entity, ReflectionUtils.withAnnotation(Id.class));
                if (fields.size() == 1) {
                    return columnDescriptorBuilder.build(ColumnDescriptorBuilder.getColumn(fields.iterator().next()));
                }
                @SuppressWarnings("unchecked")
                final Set<Method> methods = ReflectionUtils.getMethods(data.entity, ReflectionUtils.withAnnotation(Id.class));
                if (methods.size() == 1) {
                    return columnDescriptorBuilder.build(ColumnDescriptorBuilder.getColumn(methods.iterator().next()));
                }
                return null;
            }
        });

        builder.action("entity", new Action<Table>() {
            @Override
            public Object execute(Table data) {
                return classDescriptorBuilder.build(data.entity);
            }
        });

        return builder;
    }

    private Context context = new Context();

    public DescriptorBuilder<Table> tableNameResolver(TableNameResolver tableNameResolver) {
        context.tableNameResolver = tableNameResolver;
        return this;
    }

    @Override
    public DescriptorBuilder<Table> action(String fieldName, Action<Table> action) {
        if (action instanceof EntityAction) {
            final EntityAction entityAction = (EntityAction) action;
            entityAction.context = context;
        }
        return super.action(fieldName, action);
    }

    public static Table getTable(Class<?> data) {
        return new Table(data.getAnnotation(javax.persistence.Table.class), data);
    }

    public static abstract class EntityAction implements Action<Table> {

        private Context context;

        public abstract Object execute(Table data, TableNameResolver tableNameResolver);

        @Override
        public Object execute(Table data) {
            return execute(data, context.tableNameResolver);
        }

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

    private class Context {
        private TableNameResolver tableNameResolver;
    }

}
