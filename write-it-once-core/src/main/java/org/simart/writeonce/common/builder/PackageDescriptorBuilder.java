package org.simart.writeonce.common.builder;

import org.simart.writeonce.common.Action;
import org.simart.writeonce.common.DefaultDescriptorBuilder;
import org.simart.writeonce.common.DescriptorBuilder;

public class PackageDescriptorBuilder extends DefaultDescriptorBuilder<Package> {

    public static DescriptorBuilder<Package> create() {
        final PackageDescriptorBuilder buider = new PackageDescriptorBuilder();

        buider.action("name", new Action<Package>() {
            @Override
            public Object execute(Package data) {
                return data.getName();
            }
        });

        return buider;
    }

}
