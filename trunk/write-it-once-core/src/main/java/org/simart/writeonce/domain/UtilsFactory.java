package org.simart.writeonce.domain;

import org.simart.writeonce.common.Utils;

public class UtilsFactory extends AbstractDescriptorFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T create(Class<T> cls, Object data) {
        return (T) (Utils.class.isAssignableFrom(cls) ? new UtilsImpl(this.getContext()) : null);
    }

}
