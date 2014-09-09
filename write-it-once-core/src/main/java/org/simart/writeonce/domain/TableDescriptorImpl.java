package org.simart.writeonce.domain;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.getAllMethods;
import static org.reflections.ReflectionUtils.withAnnotation;
import static org.reflections.ReflectionUtils.withModifier;
import static org.reflections.ReflectionUtils.withPrefix;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.reflections.ReflectionUtils;
import org.simart.writeonce.common.ClassDescriptor;
import org.simart.writeonce.common.ColumnDescriptor;
import org.simart.writeonce.common.Help;
import org.simart.writeonce.common.TableDescriptor;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class TableDescriptorImpl implements TableDescriptor {

    private final Context context;
    private final Class<?> cls;
    private final Table table;
    private final Function<Method, ColumnDescriptor> method2Column;
    private final Function<Field, ColumnDescriptor> field2Column;

    public TableDescriptorImpl(final Context context, Class<?> cls) {
        super();
        this.context = context;
        this.cls = cls;
        this.table = cls.getAnnotation(Table.class);
        this.method2Column = new Function<Method, ColumnDescriptor>() {

            @Override
            public ColumnDescriptor apply(Method input) {
                return context.create(ColumnDescriptor.class, input);
            }
        };
        this.field2Column = new Function<Field, ColumnDescriptor>() {

            @Override
            public ColumnDescriptor apply(Field input) {
                return context.create(ColumnDescriptor.class, input);
            }
        };
    }

    @Override
    public String getSchema() {
        return table == null ? null : table.schema();
    }

    @Override
    public String getName() {
        return table == null ? context.getTableNameResolver().getName(context.create(ClassDescriptor.class, cls)) : table.name();
    }

    @Override
    public Map<String, ColumnDescriptor> getColumn() {
        final Map<String, ColumnDescriptor> result = Maps.newHashMap();
        @SuppressWarnings("unchecked")
        final Set<Field> fields = Sets.difference(getAllFields(cls), getAllFields(cls, withModifier(Modifier.STATIC)));
        ColumnDescriptor[] columnDescriptors = Collections2.transform(fields, field2Column).toArray(new ColumnDescriptor[fields.size()]);
        for (ColumnDescriptor columnDescriptor : columnDescriptors) {
            result.put(columnDescriptor.getName(), columnDescriptor);
        }
        @SuppressWarnings("unchecked")
        final Set<Method> methods = getAllMethods(this.cls, withPrefix("get"), withAnnotation(Column.class));
        columnDescriptors = Collections2.transform(methods, method2Column).toArray(new ColumnDescriptor[methods.size()]);
        for (ColumnDescriptor columnDescriptor : columnDescriptors) {
            result.put(columnDescriptor.getName(), columnDescriptor);
        }
        return result;
    }

    @Override
    public ColumnDescriptor[] getColumns() {
        return getColumn().values().toArray(new ColumnDescriptor[getColumn().values().size()]);
    }

    @Override
    public ColumnDescriptor getSinglePrimaryKey() {
        @SuppressWarnings("unchecked")
        final Set<Field> fields = ReflectionUtils.getFields(cls, ReflectionUtils.withAnnotation(Id.class));
        if (fields.size() == 1) {
            return context.create(ColumnDescriptor.class, fields.iterator().next());
        }
        @SuppressWarnings("unchecked")
        final Set<Method> methods = ReflectionUtils.getMethods(cls, ReflectionUtils.withAnnotation(Id.class));
        if (methods.size() == 1) {
            return context.create(ColumnDescriptor.class, methods.iterator().next());
        }
        return null;
    }

    @Override
    public Help get_help() {
        return new HelpFactory().create(this);
    }

    @Override
    public Object get_root() {
        return cls;
    }

    @Override
    public String toString() {
        return get_help().toString();
    }

}
