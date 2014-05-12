package org.simart.writeonce.domain;

import java.lang.reflect.Field;

import org.simart.writeonce.common.ColumnDescriptor;
import org.simart.writeonce.common.DescriptorFactory;

public class ColumnFactory implements DescriptorFactory {

    private Context context;

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> cls, Object data) {
        return (ColumnDescriptor.class.isAssignableFrom(cls) ? (T) new ColumnDescriptorImpl(context, (Field) data) : null);
    }

    public void init(Context context) {
        this.context = context;
    }

}
