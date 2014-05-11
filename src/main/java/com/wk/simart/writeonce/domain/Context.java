package com.wk.simart.writeonce.domain;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.wk.simart.writeonce.common.DescriptorFactory;

public class Context {

    private List<DescriptorFactory> descriptorFactories;

    public <T> T create(Class<T> cls, Object data) {
        for (DescriptorFactory descriptorFactory : this.descriptorFactories) {
            final T result = descriptorFactory.create(cls, data);
            if (result != null) {
                return result;
            }
        }
        throw new UnsupportedOperationException(String.format("unsupported class", cls.getName()));
    }

    void setDescriptorFactories(List<DescriptorFactory> descriptorFactories) {
        for (DescriptorFactory descriptorFactory : descriptorFactories) {
            descriptorFactory.init(this);
        }
        this.descriptorFactories = ImmutableList.copyOf(descriptorFactories);
    }
}
