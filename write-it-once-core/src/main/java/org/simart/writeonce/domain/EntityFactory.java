package org.simart.writeonce.domain;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.simart.writeonce.common.ColumnDescriptor;
import org.simart.writeonce.common.DescriptorFactory;
import org.simart.writeonce.common.EntityColumnDescriptor;
import org.simart.writeonce.common.TableDescriptor;

public class EntityFactory implements DescriptorFactory {

    private Context context;

    @Override
    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> cls, Object data) {
        T t = TableDescriptor.class.isAssignableFrom(cls) ? (T) new TableDescriptorImpl(context, (Class<?>) data) : null;
        if (t != null) {
            return t;
        }
        if (ColumnDescriptor.class.isAssignableFrom(cls)) {
            if (data instanceof Field) {
                return (T) new ColumnDescriptorImpl(context, (Field) data);
            } else if (data instanceof Method) {
                return (T) new ColumnDescriptorImpl(context, (Method) data);
            } else {
                throw new UnsupportedOperationException("unsupported data for " + cls.getName() + " is " + data);
            }
        }
        if (EntityColumnDescriptor.class.isAssignableFrom(cls)) {
            if (data instanceof Field) {
                return (T) new FieldDescriptorImpl(context, (Field) data);
            } else if (data instanceof Method) {
                return (T) new MethodDescriptorImpl(context, (Method) data);
            } else {
                throw new UnsupportedOperationException("unsupported data for " + cls.getName() + " is " + data);
            }
        }

        return null;
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }

}
