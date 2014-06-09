package org.simart.writeonce.domain;

import java.io.File;

import org.simart.writeonce.common.PackageDescriptor;
import org.simart.writeonce.common.Utils;

public class UtilsImpl implements Utils {

    // private final Context context;

    public UtilsImpl(Context context) {
        super();
        // this.context = context;
    }

    @Override
    public String package2Path(PackageDescriptor packageDescriptor) {
        if (packageDescriptor == null) {
            return "";
        }
        final String[] parts = packageDescriptor.getName().split("\\.");
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            builder.append(parts[i]).append(File.separator);
        }
        return builder.toString();
    }

}
