package org.simart.writeonce.domain;

import org.simart.writeonce.common.ColumnNameResolver;

public class DefaultColumnNameResolver implements ColumnNameResolver {

    @Override
    public String getName(String fieldName) {
        return fieldName.toUpperCase();
    }

}
