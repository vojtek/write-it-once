package org.simart.writeonce.common.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.reflections.ReflectionUtils;
import org.simart.writeonce.application.Context;
import org.simart.writeonce.common.Action;

import com.google.common.collect.Lists;

public class AnnotationDescriptorBuilder extends DefaultDescriptorBuilder<Annotation> {

    public static DescriptorBuilder<Annotation> create() {
	final AnnotationDescriptorBuilder builder = new AnnotationDescriptorBuilder();

	builder.action("name", new Action<Annotation>() {
	    @Override
	    public Object execute(Annotation data, Context context) {
		return context.getBuilder(Class.class).build(data.annotationType(), context).get("name");
	    }
	});
	builder.action("type", new Action<Annotation>() {
	    @Override
	    public Object execute(Annotation data, Context context) {
		return context.getBuilder(Class.class).build(data.annotationType(), context);
	    }
	});

	builder.action("attribute", new Action<Annotation>() {
	    @Override
	    public Object execute(Annotation data, Context context) {
		return data;
	    }
	});

	return builder;
    }

    @SuppressWarnings("unchecked")
    public static List<Annotation> getAllAnnotations(Class<?> type) {
	return Lists.newArrayList(ReflectionUtils.getAllAnnotations(type));
    }

    @SuppressWarnings("unchecked")
    public static List<Annotation> getAllAnnotations(Method type) {
	return Lists.newArrayList(ReflectionUtils.getAllAnnotations(type));
    }

    @SuppressWarnings("unchecked")
    public static List<Annotation> getAllAnnotations(Field type) {
	return Lists.newArrayList(ReflectionUtils.getAllAnnotations(type));
    }

}
