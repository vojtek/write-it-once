package org.simart.writeonce.domain;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.simart.writeonce.common.AnnotationDescriptor;
import org.simart.writeonce.common.ClassDescriptor;
import org.simart.writeonce.common.FieldDescriptor;
import org.simart.writeonce.common.GeneratorRuntimeException;
import org.simart.writeonce.common.MethodDescriptor;
import org.simart.writeonce.common.PackageDescriptor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

public class AnnotationDescriptorImpl implements AnnotationDescriptor {

    // private final Context context;
    private final Annotation annotation;
    private final ClassDescriptor classDescriptor;

    public AnnotationDescriptorImpl(Context context, Annotation annotation) {
	super();
	// this.context = context;
	this.annotation = annotation;
	this.classDescriptor = context.create(ClassDescriptor.class, this.annotation.annotationType());
    }

    @Override
    public String getName() {
	return classDescriptor.getName();
    }

    @Override
    public String getShortName() {
	return classDescriptor.getShortName();
    }

    @Override
    public PackageDescriptor getPackage() {
	return classDescriptor.getPackage();
    }

    @Override
    public AnnotationDescriptor[] getAnnotations() {
	return classDescriptor.getAnnotations();
    }

    @Override
    public Map<String, AnnotationDescriptor> getAnnotation() {
	return classDescriptor.getAnnotation();
    }

    @Override
    public MethodDescriptor[] getMethods() {
	return classDescriptor.getMethods();
    }

    @Override
    public Map<String, MethodDescriptor> getMethod() {
	return classDescriptor.getMethod();
    }

    public FieldDescriptor[] getFields() {
	return classDescriptor.getFields();
    }

    public Map<String, FieldDescriptor> getField() {
	return classDescriptor.getField();
    }

    @Override
    public Annotation getAttributes() {
	return this.annotation;
    }

    @Override
    public Map<String, Object> getAttribute() {
	final List<String> ignoreMethodsNames = ImmutableList.of("annotationType", "equals", "toString", "hashCode");
	final Map<String, Object> result = Maps.newHashMap();
	final Method[] methods = this.annotation.annotationType().getMethods();
	for (Method method : methods) {
	    if (ignoreMethodsNames.contains(method.getName())) {
		continue;
	    }
	    try {
		result.put(method.getName(), method.invoke(this.annotation, new Object[0]));
	    } catch (IllegalAccessException e) {
		throw new GeneratorRuntimeException(String.format("unable to get annotation value %s", method), e);
	    } catch (IllegalArgumentException e) {
		throw new GeneratorRuntimeException(String.format("unable to get annotation value %s", method), e);
	    } catch (InvocationTargetException e) {
		throw new GeneratorRuntimeException(String.format("unable to get annotation value %s", method), e);
	    }
	}
	return result;
    }
}
