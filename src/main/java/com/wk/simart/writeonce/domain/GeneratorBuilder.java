package com.wk.simart.writeonce.domain;

import java.util.LinkedList;

import com.google.common.collect.ImmutableList;
import com.wk.simart.writeonce.common.DescriptorFactory;
import com.wk.simart.writeonce.common.Generator;

public class GeneratorBuilder {

    private static GeneratorBuilder empty() {
        return new GeneratorBuilder();
    }

    public static GeneratorBuilder instance() {
        return empty()
                .add(new TypeFactory())
                .add(new FieldFactory())
                .add(new MethodFactory())
                .add(new ParameterFactory())
                .add(new PackageFactory());
    }

    private final LinkedList<DescriptorFactory> descriptorFactories = new LinkedList<DescriptorFactory>();

    public GeneratorBuilder override(DescriptorFactory descriptorFactory) {
        descriptorFactories.offerFirst(descriptorFactory);
        return this;
    }

    public GeneratorBuilder add(DescriptorFactory descriptorFactory) {
        descriptorFactories.add(descriptorFactory);
        return this;
    }

    public Generator build() {
        final Context context = new Context();
        context.setDescriptorFactories(ImmutableList.copyOf(descriptorFactories));
        final Generator generator = new GeneratorImpl(context);
        return generator;
    }
}
