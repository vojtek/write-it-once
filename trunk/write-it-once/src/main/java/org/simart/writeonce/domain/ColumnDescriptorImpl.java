package org.simart.writeonce.domain;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.Column;

import org.simart.writeonce.common.ColumnDescriptor;
import org.simart.writeonce.common.FieldDescriptor;

public class ColumnDescriptorImpl implements ColumnDescriptor {

    private final Context context;
    private final Field field;
    private final Method method;
    private final Column column;

    public ColumnDescriptorImpl(Context context, Field field) {
        super();
        this.context = context;
        this.field = field;
        this.method = null;
        this.column = this.field.getAnnotation(Column.class);
    }

    public ColumnDescriptorImpl(Context context, Method method) {
        super();
        this.context = context;
        this.field = null;
        this.method = method;
        this.column = this.method.getAnnotation(Column.class);
    }

    public String getName() {
        if (this.column != null && !this.column.name().isEmpty()) {
            return this.column.name();
        }
        if (this.field != null) {
            return context.getColumnNameResolver().getName(context.create(FieldDescriptor.class, this.field));
        } else {
            return context.getColumnNameResolver().getName(context.create(FieldDescriptor.class, this.field));
        }
    }

    public String getType() {
        if (this.field != null) {
            return context.getColumnTypeResolver().getType(column, this.field.getType());
        } else {
            return context.getColumnTypeResolver().getType(column, this.method.getReturnType());
        }
    }

    public String getFullType() {
        if (this.field != null) {
            return context.getColumnTypeResolver().getFullType(column, this.field.getType());
        } else {
            return context.getColumnTypeResolver().getFullType(column, this.method.getReturnType());
        }
    }

    public Integer getLength() {
        return this.column == null ? 0 : this.column.length();
    }

    public Integer getPrecision() {
        return this.column == null ? 0 : this.column.precision();
    }

    public Integer getScale() {
        return this.column == null ? 0 : this.column.scale();
    }

    public Boolean isNullable() {
        return this.column == null ? false : this.column.nullable();
    }

}
