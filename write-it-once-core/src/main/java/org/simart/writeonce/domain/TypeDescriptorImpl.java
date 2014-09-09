package org.simart.writeonce.domain;

import org.simart.writeonce.common.Help;
import org.simart.writeonce.common.PackageDescriptor;
import org.simart.writeonce.common.TypeDescriptor;

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

    public PackageDescriptor getPackage() {
        return context.create(PackageDescriptor.class, cls.getPackage());
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
