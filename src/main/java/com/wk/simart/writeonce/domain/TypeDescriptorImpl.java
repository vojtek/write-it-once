package com.wk.simart.writeonce.domain;

import com.wk.simart.writeonce.common.PackageDescriptor;
import com.wk.simart.writeonce.common.TypeDescriptor;

public class TypeDescriptorImpl implements TypeDescriptor {

    final Context context;
    final Class<?> cls;

    public TypeDescriptorImpl(Context context, Class<?> cls) {
        super();
        this.context = context;
        this.cls = cls;
    }

    public String getName() {
        return cls.getName();
    }

    public String getShortName() {
        return cls.getSimpleName();
    }

    @Override
    public String toString() {
        return getName();
    }

    public PackageDescriptor getPackage() {
        return context.create(PackageDescriptor.class, cls.getPackage());
    }

}
