package org.simart.writeonce.common.builder;

import java.util.Map;

import org.simart.writeonce.application.Context;
import org.simart.writeonce.common.Action;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class DefaultDescriptorBuilder<E extends Object> implements DescriptorBuilder<E> {

    final Map<String, Object> content = Maps.newHashMap();

    public DescriptorBuilder<E> action(String fieldName, Action<E> action) {
        content.put(fieldName, action);
        return this;
    }

    public DescriptorBuilder<E> value(String fieldName, String value) {
        content.put(fieldName, value);
        return this;
    }

    @Override
    public Descriptor<E> build(E object, Context context) {
        return Descriptors.newDescriptor(object, content, context);
    }

    @Override
    public DescriptorBuilder<E> merge(DescriptorBuilder<?> descriptorBuilder) {
        content.putAll(descriptorBuilder.getContent());
        return null;
    }

    @Override
    public Map<String, Object> getContent() {
        return ImmutableMap.copyOf(content);
    }

}
