package org.simart.writeonce.domain;

import org.simart.writeonce.common.PackageDescriptor;

public class PackageFactory extends AbstractDescriptorFactory {

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> cls, Object data) {
        return PackageDescriptor.class.isAssignableFrom(cls) ? (T) new PackageDescriptorImpl(getContext(), (Package) data) : null;
    }

}
