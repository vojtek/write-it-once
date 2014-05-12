package com.wk.simart.writeonce.domain;

import java.lang.reflect.Parameter;

import com.wk.simart.writeonce.common.ParameterDescriptor;

public class ParameterFactory extends AbstractDescriptorFactory {

    private ParameterDescriptor create(Parameter parameter, int position) {
        return new ParameterDescriptorImpl(getContext(), parameter, position);
    }

    private ParameterDescriptor[] create(Parameter[] parameters) {
        final ParameterDescriptor[] result = new ParameterDescriptor[parameters.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = create(parameters[i], i + 1);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> cls, Object data) {
        return ParameterDescriptor[].class.isAssignableFrom(cls) ? (T) create((Parameter[]) data) : null;
    }

}
