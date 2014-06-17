package org.simart.writeonce.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.Column;

public interface ColumnTypeResolver {

    String getType(Column column, Class<?> typeClass, Field field, Method method);

    String getFullType(Column column, Class<?> typeClass, Field field, Method method);
}
