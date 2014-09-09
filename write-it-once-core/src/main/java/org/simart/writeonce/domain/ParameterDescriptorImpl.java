package org.simart.writeonce.domain;

import java.lang.reflect.Parameter;

import org.simart.writeonce.common.ClassDescriptor;
import org.simart.writeonce.common.Help;
import org.simart.writeonce.common.ParameterDescriptor;

public class ParameterDescriptorImpl implements ParameterDescriptor {

    private final Context context;
    private final Parameter parameter;
    private final int position;

    public ParameterDescriptorImpl(Context context, Parameter parameter, final int position) {
        this.context = context;
        this.parameter = parameter;
        this.position = position;
    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    public String getName() {
        return parameter.getName();
    }

    @Override
    public ClassDescriptor getType() {
        return this.context.create(ClassDescriptor.class, parameter.getType());
    }

    @Override
    public Help get_help() {
        return new HelpFactory().create(this);
    }

    @Override
    public Object get_root() {
        return parameter;
    }

    @Override
    public String toString() {
        return get_help().toString();
    }

}
