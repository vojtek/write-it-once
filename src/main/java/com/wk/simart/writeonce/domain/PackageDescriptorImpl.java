package com.wk.simart.writeonce.domain;

import com.wk.simart.writeonce.common.PackageDescriptor;

public class PackageDescriptorImpl implements PackageDescriptor {

    // private final Context context;
    private final Package pcg;

    public PackageDescriptorImpl(Context context, Package pcg) {
        super();
        // this.context = context;
        this.pcg = pcg;
    }

    public String getName() {
        return pcg.getName();
    }

}
