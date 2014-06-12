package org.simart.writeonce.domain;

import org.simart.writeonce.common.DescriptorFactory;
import org.simart.writeonce.common.TableDescriptor;

public class TableFactory implements DescriptorFactory {

    private Context context;

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> cls, Object data) {
        return (TableDescriptor.class.isAssignableFrom(cls) ? (T) new TableDescriptorImpl(context, (Class<?>) data) : null);
    }

    public void init(Context context) {
        this.context = context;
    }

}
