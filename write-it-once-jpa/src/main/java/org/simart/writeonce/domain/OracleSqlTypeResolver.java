package org.simart.writeonce.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.simart.writeonce.common.ColumnTypeResolver;

public class OracleSqlTypeResolver implements ColumnTypeResolver {

    @Override
    public String getFullType(ColumnTypeResolver.TypeDescriptor typeDescriptor) {
	int precision = typeDescriptor.getColumn() == null ? 0 : typeDescriptor.getColumn().precision();
	int scale = typeDescriptor.getColumn() == null ? 0 : typeDescriptor.getColumn().scale();
	int length = typeDescriptor.getColumn() == null ? 0 : typeDescriptor.getColumn().length();
	if (String.class.isAssignableFrom(typeDescriptor.getTypeClass())) {
	    return "VARCHAR(CHAR " + length + ")";
	}
	if (BigDecimal.class.isAssignableFrom(typeDescriptor.getTypeClass())) {
	    return "NUMBER(" + precision + ", " + scale + ")";
	}
	if (BigInteger.class.isAssignableFrom(typeDescriptor.getTypeClass())) {
	    return "NUMBER(" + precision + ", " + scale + ")";
	}
	if (Long.class.isAssignableFrom(typeDescriptor.getTypeClass())) {
	    return "NUMBER(" + precision + ", " + scale + ")";
	}
	if (Integer.class.isAssignableFrom(typeDescriptor.getTypeClass())) {
	    return "NUMBER(" + precision + ", " + scale + ")";
	}
	if (Short.class.isAssignableFrom(typeDescriptor.getTypeClass())) {
	    return "NUMBER(" + precision + ", " + scale + ")";
	}
	if (Double.class.isAssignableFrom(typeDescriptor.getTypeClass())) {
	    return "NUMBER(" + precision + ", " + scale + ")";
	}
	if (Float.class.isAssignableFrom(typeDescriptor.getTypeClass())) {
	    return "NUMBER(" + precision + ", " + scale + ")";
	}
	if (Date.class.isAssignableFrom(typeDescriptor.getTypeClass())) {
	    return "DATE";
	}
	return null;
    }

    @Override
    public String getType(ColumnTypeResolver.TypeDescriptor typeDescriptor) {
	if (String.class.isAssignableFrom(typeDescriptor.getTypeClass())) {
	    return "VARCHAR";
	}
	if (BigDecimal.class.isAssignableFrom(typeDescriptor.getTypeClass())) {
	    return "NUMBER";
	}
	if (BigInteger.class.isAssignableFrom(typeDescriptor.getTypeClass())) {
	    return "NUMBER";
	}
	if (Long.class.isAssignableFrom(typeDescriptor.getTypeClass())) {
	    return "NUMBER";
	}
	if (Integer.class.isAssignableFrom(typeDescriptor.getTypeClass())) {
	    return "NUMBER";
	}
	if (Short.class.isAssignableFrom(typeDescriptor.getTypeClass())) {
	    return "NUMBER";
	}
	if (Double.class.isAssignableFrom(typeDescriptor.getTypeClass())) {
	    return "NUMBER";
	}
	if (Float.class.isAssignableFrom(typeDescriptor.getTypeClass())) {
	    return "NUMBER";
	}
	if (Date.class.isAssignableFrom(typeDescriptor.getTypeClass())) {
	    return "DATE";
	}
	return null;
    }

}
