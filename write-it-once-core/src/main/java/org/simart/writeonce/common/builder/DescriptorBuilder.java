package org.simart.writeonce.common.builder;

import java.util.Map;

import org.simart.writeonce.common.Action;

public interface DescriptorBuilder<E extends Object> {

    DescriptorBuilder<E> action(String fieldName, Action<E> action);

    DescriptorBuilder<E> value(String fieldName, String value);

    DescriptorBuilder<E> merge(DescriptorBuilder<?> descriptorBuilder);

    Descriptor<E> build(E data);

    Map<String, Object> getContent();
}
