package org.simart.writeonce.domain;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.Column;

import org.simart.writeonce.common.ColumnDescriptor;
import org.simart.writeonce.common.DescriptorFactory;
import org.simart.writeonce.common.EntityColumnDescriptor;
import org.simart.writeonce.common.TableDescriptor;
import org.simart.writeonce.utils.StringUtils;

public class EntityFactory implements DescriptorFactory {

    private Context context;

    @Override
    public void init(Context context) {
	this.context = context;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> cls, Object data) {
	T t = TableDescriptor.class.isAssignableFrom(cls) ? (T) new TableDescriptorImpl(context, (Class<?>) data) : null;
	if (t != null) {
	    return t;
	}
	if (ColumnDescriptor.class.isAssignableFrom(cls)) {
	    final Object columnHolder = findColumnObject(data);
	    if (columnHolder instanceof Method) {
		return (T) new ColumnDescriptorImpl(context, (Method) columnHolder);
	    } else {
		return (T) new ColumnDescriptorImpl(context, (Field) columnHolder);
	    }
	}
	if (EntityColumnDescriptor.class.isAssignableFrom(cls)) {
	    final Object columnHolder = findColumnObject(data);
	    if (columnHolder instanceof Method) {
		return (T) new MethodDescriptorImpl(context, (Method) columnHolder);
	    } else {
		return (T) new FieldDescriptorImpl(context, (Field) columnHolder);
	    }
	}

	return null;
    }

    Object findColumnObject(Object o) {
	try {
	    if (o instanceof Method) {
		final Method method = (Method) o;
		if (method.isAnnotationPresent(Column.class)) {
		    return method;
		} else {
		    return getBeanField(method);
		}
	    } else if (o instanceof Field) {
		final Field field = (Field) o;
		if (field.isAnnotationPresent(Column.class)) {
		    return field;
		} else {
		    final Method method = getBeanMethod(field);
		    if (method.isAnnotationPresent(Column.class)) {
			return method;
		    } else {
			return field;
		    }
		}
	    }
	} catch (NoSuchFieldException e) {
	    throw new RuntimeException(e);
	} catch (SecurityException e) {
	    throw new RuntimeException(e);
	} catch (NoSuchMethodException e) {
	    throw new RuntimeException(e);
	}
	throw new UnsupportedOperationException("unsupported type: " + o);
    }

    Field getBeanField(Method method) throws NoSuchFieldException, SecurityException {
	return method.getDeclaringClass().getField(StringUtils.uncapitalize(method.getName().substring(3)));
    }

    Method getBeanMethod(Field field) throws SecurityException, NoSuchMethodException {
	return field.getDeclaringClass().getMethod("get" + StringUtils.capitalize(field.getName()), new Class[0]);
    }

}
