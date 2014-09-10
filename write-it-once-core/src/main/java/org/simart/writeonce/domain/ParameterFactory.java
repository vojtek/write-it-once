package org.simart.writeonce.domain;

import org.simart.writeonce.common.ParameterDescriptor;

public class ParameterFactory extends AbstractDescriptorFactory {

    private ParameterDescriptor create(Class<?> parameter, int position) {
        return new ParameterDescriptorImpl(getContext(), parameter, position);
    }

    private ParameterDescriptor[] create(Class<?>[] parameters) {
        final ParameterDescriptor[] result = new ParameterDescriptor[parameters.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = create(parameters[i], i + 1);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> cls, Object data) {
        return ParameterDescriptor[].class.isAssignableFrom(cls) ? (T) create((Class<?>[]) data) : null;
    }

}
