package org.simart.writeonce.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import javax.persistence.Column;

import com.google.common.collect.Maps;

public interface ColumnTypeResolver {

    String getType(TypeDescriptor typeDescriptor);

    String getFullType(TypeDescriptor typeDescriptor);

    static final class TypeDescriptor {
	private final Column column;
	private final Class<?> typeClass;
	private final Field field;
	private final Map<String, Annotation> annotations = Maps.newHashMap();

	public TypeDescriptor(Column column, Class<?> typeClass, Field field, Collection<Annotation> annotations) {
	    super();
	    this.column = column;
	    this.typeClass = typeClass;
	    this.field = field;
	    for (Annotation annotation : annotations) {
		this.annotations.put(annotation.annotationType().getName(), annotation);
	    }
	}

	public Column getColumn() {
	    return column;
	}

	public Class<?> getTypeClass() {
	    return typeClass;
	}

	public Field getField() {
	    return field;
	}

	@SuppressWarnings("unchecked")
	public <E> E getAnnotation(Class<E> annotationType) {
	    return (E) annotations.get(annotationType.getName());
	}

	public Collection<Annotation> getAnnotations() {
	    return annotations.values();
	}

    }
}
