package com.wk.simart.writeonce.domain;

import java.lang.reflect.Parameter;

import com.wk.simart.writeonce.common.ParameterDescriptor;

public class ParameterDescriptorImpl extends ClassDescriptorImpl implements ParameterDescriptor {

    private final Parameter parameter;
    private final int position;

    public ParameterDescriptorImpl(Context context, Parameter parameter, final int position) {
        super(context, parameter.getType());
        this.parameter = parameter;
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }

}
