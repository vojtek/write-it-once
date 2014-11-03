package org.simart.writeonce.common.builder;

import java.io.File;

import org.simart.writeonce.common.Action;

public class PackageDescriptorBuilder extends DefaultDescriptorBuilder<Package> {

    public static DescriptorBuilder<Package> create() {
        final PackageDescriptorBuilder buider = new PackageDescriptorBuilder();

        buider.action("name", new Action<Package>() {
            @Override
            public Object execute(Package data) {
                return data.getName();
            }
        });

        buider.action("path", new Action<Package>() {
            @Override
            public Object execute(Package data) {
                final String[] parts = data.getName().split("\\.");
                final StringBuilder builder = new StringBuilder();
                for (int i = 0; i < parts.length; i++) {
                    builder.append(parts[i]).append(File.separator);
                }
                return builder.toString();
            }
        });

        return buider;
    }

}
