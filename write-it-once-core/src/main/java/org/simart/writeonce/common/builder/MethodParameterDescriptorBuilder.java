package org.simart.writeonce.common.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.simart.writeonce.application.Context;
import org.simart.writeonce.common.Action;
import org.simart.writeonce.common.builder.MethodParameterDescriptorBuilder.MethodParameter;

import com.google.common.collect.Lists;

public class MethodParameterDescriptorBuilder extends DefaultDescriptorBuilder<MethodParameter> {

    public static DescriptorBuilder<MethodParameter> create() {
	final MethodParameterDescriptorBuilder builder = new MethodParameterDescriptorBuilder();

	builder.action("annotations", new Action<MethodParameter>() {
	    @Override
	    public Object execute(final MethodParameter data, Context context) {
		return DescriptorBuilders.build(context.getBuilder(Annotation.class), Arrays.asList(data.annotations), context);
	    }
	});
	builder.action("annotation", new Action<MethodParameter>() {
	    @Override
	    public Object execute(final MethodParameter data, Context context) {
		return Descriptors.extract("name", DescriptorBuilders.build(context.getBuilder(Annotation.class), Arrays.asList(data.annotations), context));
	    }
	});

	builder.action("type", new Action<MethodParameter>() {
	    @Override
	    public Object execute(MethodParameter data, Context context) {
		return context.getBuilder(Class.class).build(data.type, context);
	    }
	});

	builder.action("index", new Action<MethodParameter>() {
	    @Override
	    public Object execute(MethodParameter data, Context context) {
		return data.index;
	    }
	});

	return builder;
    }

    public static List<MethodParameter> getAllMethodParameters(Method data) {
	final List<MethodParameter> methodParameters = Lists.newArrayList();
	final Annotation[][] parameterAnnotations = data.getParameterAnnotations();
	final Class<?>[] parameterTypes = data.getParameterTypes();
	for (int i = 0; i < parameterTypes.length; i++) {
	    methodParameters.add(new MethodParameter(parameterAnnotations[i], parameterTypes[i], i));
	}
	return methodParameters;
    }

    public static class MethodParameter {
	private final Annotation[] annotations;
	private final Class<?> type;
	private final int index;

	public MethodParameter(Annotation[] annotations, Class<?> type, int index) {
	    super();
	    this.annotations = annotations;
	    this.type = type;
	    this.index = index;
	}

    }

}
