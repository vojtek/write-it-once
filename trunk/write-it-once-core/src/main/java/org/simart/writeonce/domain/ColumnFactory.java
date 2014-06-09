package org.simart.writeonce.domain;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.Column;

import org.simart.writeonce.common.ColumnDescriptor;
import org.simart.writeonce.common.DescriptorFactory;

public class ColumnFactory implements DescriptorFactory {

    private Context context;

    @Override
    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> cls, Object data) {
        if (ColumnDescriptor.class.isAssignableFrom(cls)) {
            if (data instanceof Field) {
                final Field field = (Field) data;
                if (field.getAnnotation(Column.class) == null) {
                    return null;
                }
                return (T) new ColumnDescriptorImpl(context, (Field) data);
            } else if (data instanceof Method) {
                final Method method = (Method) data;
                if (method.getAnnotation(Column.class) == null) {
                    return null;
                }
                return (T) new ColumnDescriptorImpl(context, (Method) data);
            } else {
                throw new UnsupportedOperationException("unsupported data type: " + data);
            }
        }
        return null;
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }

}
