package org.simart.writeonce.application;

import java.util.Map;

import org.simart.writeonce.common.builder.DescriptorBuilder;

import com.google.common.collect.Maps;

public class Context {

    final Map<Class<?>, DescriptorBuilder<?>> builders = Maps.newHashMap();
    final Map<String, Object> parameters = Maps.newHashMap();
    final Map<Class<?>, Object> helpers = Maps.newHashMap();

    public <T> void register(Class<?> type, DescriptorBuilder<?> descriptorBuilder) {
	Class<?> cls = type;
	while (cls != null) {
	    builders.put(cls, descriptorBuilder);
	    cls = cls.getSuperclass();
	}
    }

    @SuppressWarnings("unchecked")
    public <T> DescriptorBuilder<T> getBuilder(Class<T> type) {
	final DescriptorBuilder<T> builder = (DescriptorBuilder<T>) builders.get(type);
	return builder;
    }

    public <E> void setHelper(Class<? extends E> type, E helper) {
	if (helper == null) {
	    this.helpers.remove(type);
	}
	this.helpers.put(type, helper);
    }

    @SuppressWarnings("unchecked")
    public <E> E getHelper(Class<E> helperType) {
	final E e = (E) this.helpers.get(helperType);
	if (e == null) {
	    throw new RuntimeException("undefined helper: " + helperType.getName());
	}
	return e;
    }

    public Context() {
    }
}
