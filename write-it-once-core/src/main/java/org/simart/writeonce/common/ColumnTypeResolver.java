package org.simart.writeonce.common;

import java.lang.reflect.Field;

import javax.persistence.Column;

public interface ColumnTypeResolver {

    String getType(Column column, Class<?> typeClass, Field field);

    String getFullType(Column column, Class<?> typeClass, Field field);
}
