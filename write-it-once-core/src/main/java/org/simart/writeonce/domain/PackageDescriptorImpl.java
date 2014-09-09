package org.simart.writeonce.domain;

import org.simart.writeonce.common.Help;
import org.simart.writeonce.common.PackageDescriptor;

public class PackageDescriptorImpl implements PackageDescriptor {

    // private final Context context;
    private final Package pcg;

    public PackageDescriptorImpl(Context context, Package pcg) {
        super();
        // this.context = context;
        this.pcg = pcg;
    }

    @Override
    public String getName() {
        return pcg.getName();
    }

    @Override
    public Help get_help() {
        return new HelpFactory().create(this);
    }

    @Override
    public Object get_root() {
        return pcg;
    }

    @Override
    public String toString() {
        return get_help().toString();
    }
}
