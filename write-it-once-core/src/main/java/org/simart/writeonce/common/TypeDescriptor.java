package org.simart.writeonce.common;

public interface TypeDescriptor extends ElementaryDescriptor {

    String getName();

    String getShortName();

    PackageDescriptor getPackage();

}
