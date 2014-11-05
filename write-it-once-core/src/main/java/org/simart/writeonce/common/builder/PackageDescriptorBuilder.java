package org.simart.writeonce.common.builder;

import java.io.File;

import org.simart.writeonce.common.Action;

public class PackageDescriptorBuilder extends DefaultDescriptorBuilder<Package> {

    public static PackageDescriptorBuilder create() {
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
                final StringBuilder result = new StringBuilder();
                result.append(buider.sourcePatch);
                for (int i = 0; i < parts.length; i++) {
                    result.append(parts[i]).append(File.separator);
                }
                return result.toString();
            }
        });

        return buider;
    }

    private String sourcePatch = "src" + File.separator + "main" + File.separator + "java" + File.separator;

    public PackageDescriptorBuilder sourcePatch(String sourcePatch) {
        this.sourcePatch = sourcePatch;
        return this;
    }
}
