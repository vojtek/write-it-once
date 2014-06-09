package org.simart.writeonce.common;

import javax.persistence.Column;

public interface ColumnTypeResolver {

    String getType(Column column, Class<?> typeClass);

    String getFullType(Column column, Class<?> typeClass);
}
