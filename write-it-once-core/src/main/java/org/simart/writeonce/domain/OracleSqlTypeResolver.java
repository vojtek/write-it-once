package org.simart.writeonce.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;

import org.simart.writeonce.common.ColumnTypeResolver;

public class OracleSqlTypeResolver implements ColumnTypeResolver {

    @Override
    public String getFullType(Column column, Class<?> typeClass) {
        int precision = column == null ? 0 : column.precision();
        int scale = column == null ? 0 : column.scale();
        int length = column == null ? 0 : column.length();
        if (String.class.isAssignableFrom(typeClass)) {
            return "VARCHAR(CHAR " + length + ")";
        }
        if (BigDecimal.class.isAssignableFrom(typeClass)) {
            return "NUMBER(" + precision + ", " + scale + ")";
        }
        if (BigInteger.class.isAssignableFrom(typeClass)) {
            return "NUMBER(" + precision + ", " + scale + ")";
        }
        if (Long.class.isAssignableFrom(typeClass)) {
            return "NUMBER(" + precision + ", " + scale + ")";
        }
        if (Integer.class.isAssignableFrom(typeClass)) {
            return "NUMBER(" + precision + ", " + scale + ")";
        }
        if (Short.class.isAssignableFrom(typeClass)) {
            return "NUMBER(" + precision + ", " + scale + ")";
        }
        if (Double.class.isAssignableFrom(typeClass)) {
            return "NUMBER(" + precision + ", " + scale + ")";
        }
        if (Float.class.isAssignableFrom(typeClass)) {
            return "NUMBER(" + precision + ", " + scale + ")";
        }
        if (Date.class.isAssignableFrom(typeClass)) {
            return "DATE";
        }
        return null;
    }

    @Override
    public String getType(Column column, Class<?> typeClass) {
        if (String.class.isAssignableFrom(typeClass)) {
            return "VARCHAR";
        }
        if (BigDecimal.class.isAssignableFrom(typeClass)) {
            return "NUMBER";
        }
        if (BigInteger.class.isAssignableFrom(typeClass)) {
            return "NUMBER";
        }
        if (Long.class.isAssignableFrom(typeClass)) {
            return "NUMBER";
        }
        if (Integer.class.isAssignableFrom(typeClass)) {
            return "NUMBER";
        }
        if (Short.class.isAssignableFrom(typeClass)) {
            return "NUMBER";
        }
        if (Double.class.isAssignableFrom(typeClass)) {
            return "NUMBER";
        }
        if (Float.class.isAssignableFrom(typeClass)) {
            return "NUMBER";
        }
        if (Date.class.isAssignableFrom(typeClass)) {
            return "DATE";
        }
        return null;
    }

}
