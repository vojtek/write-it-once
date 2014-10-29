package org.simart.writeonce.common.builder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.simart.writeonce.common.Action;
import org.simart.writeonce.common.ColumnNameResolver;
import org.simart.writeonce.common.ColumnTypeResolver;
import org.simart.writeonce.common.DefaultDescriptorBuilder;
import org.simart.writeonce.common.Descriptor;
import org.simart.writeonce.common.DescriptorBuilder;
import org.simart.writeonce.common.builder.ColumnDescriptorBuilder.Column;
import org.testng.collections.Lists;

import com.google.common.base.Preconditions;

public class ColumnDescriptorBuilder extends DefaultDescriptorBuilder<Column> {

    public static final ColumnDescriptorBuilder create() {
        final ColumnDescriptorBuilder builder = new ColumnDescriptorBuilder();

        builder.action("name", new ColumnAction() {
            @Override
            public Object execute(Column data, ColumnNameResolver columnNameResolver, ColumnTypeResolver columnTypeResolver) {
                if (data.column != null && !data.column.name().isEmpty()) {
                    return data.column.name();
                }
                if (columnNameResolver == null) {
                    throw new RuntimeException("undefined column name resolver");
                }
                return columnNameResolver.getName(data.field.getName());
            }
        });

        builder.action("type", new ColumnAction() {
            @Override
            public Object execute(Column data, ColumnNameResolver columnNameResolver, ColumnTypeResolver columnTypeResolver) {
                if (columnTypeResolver == null) {
                    throw new RuntimeException("undefined column type resolver");
                }
                return columnTypeResolver.getType(data.column, data.field.getType(), data.field);
            }
        });
        builder.action("fullType", new ColumnAction() {
            @Override
            public Object execute(Column data, ColumnNameResolver columnNameResolver, ColumnTypeResolver columnTypeResolver) {
                if (columnTypeResolver == null) {
                    throw new RuntimeException("undefined column type resolver");
                }
                return columnTypeResolver.getFullType(data.column, data.field.getType(), data.field);
            }
        });

        builder.action("length", new Action<Column>() {
            @Override
            public Object execute(Column data) {
                return data.column == null ? 0 : data.column.length();
            }
        });
        builder.action("nullable", new Action<Column>() {
            @Override
            public Object execute(Column data) {
                return data.column == null ? false : data.column.nullable();
            }
        });
        builder.action("scale", new Action<Column>() {
            @Override
            public Object execute(Column data) {
                return data.column == null ? 0 : data.column.scale();
            }
        });
        builder.action("precision", new Action<Column>() {
            @Override
            public Object execute(Column data) {
                return data.column == null ? 0 : data.column.precision();
            }
        });

        return builder;
    }

    public static List<Column> getColumns(Class<?> cls) {
        final List<Column> columns = Lists.newArrayList();
        for (Field field : cls.getDeclaredFields()) {
            columns.add(getColumn(field));
        }
        return columns;
    }

    public static Column getColumn(Field data) {
        javax.persistence.Column column = data.getAnnotation(javax.persistence.Column.class);
        if (column != null) {
            return new Column(data.getAnnotation(javax.persistence.Column.class), data);
        }
        @SuppressWarnings("unchecked")
        final Descriptor<Method> methodDescriptor = (Descriptor<Method>) FieldDescriptorBuilder.ACTION_GETTER.execute(data);
        return getColumn(methodDescriptor.getData());
    }

    public static Column getColumn(Method data) {
        javax.persistence.Column column = data.getAnnotation(javax.persistence.Column.class);
        @SuppressWarnings("unchecked")
        final Descriptor<Field> fieldDescriptor = (Descriptor<Field>) MethodDescriptorBuilder.ACTION_PROPERTY.execute(data);
        Preconditions.checkNotNull(fieldDescriptor);
        final Field field = fieldDescriptor.getData();
        if (column == null) {
            column = field.getAnnotation(javax.persistence.Column.class);
        }
        return new Column(field.getAnnotation(javax.persistence.Column.class), field);
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

    private Context context = new Context();

    public ColumnDescriptorBuilder columnNameResolver(ColumnNameResolver columnNameResolver) {
        context.columnNameResolver = columnNameResolver;
        return this;
    }

    public ColumnDescriptorBuilder columnTypeResolver(ColumnTypeResolver columnTypeResolver) {
        context.columnTypeResolver = columnTypeResolver;
        return this;
    }

    @Override
    public DescriptorBuilder<Column> action(String fieldName, Action<Column> action) {
        if (action instanceof ColumnAction) {
            final ColumnAction columnAction = (ColumnAction) action;
            columnAction.context = context;
        }
        return super.action(fieldName, action);
    }

    public static abstract class ColumnAction implements Action<Column> {
        private Context context;

        public abstract Object execute(Column data, ColumnNameResolver columnNameResolver, ColumnTypeResolver columnTypeResolver);

        @Override
        public Object execute(Column data) {
            return execute(data, context.columnNameResolver, context.columnTypeResolver);
        }

    }

    private class Context {
        private ColumnNameResolver columnNameResolver;
        private ColumnTypeResolver columnTypeResolver;
    }

}
