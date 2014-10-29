package org.simart.writeonce.common.builder;

import java.util.List;

import org.simart.writeonce.common.Descriptor;
import org.simart.writeonce.common.DescriptorBuilder;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class DescriptorBuilders {

    public static <E> List<Descriptor<E>> build(final DescriptorBuilder<E> builder, List<E> datas) {
        return Lists.transform(datas, new Function<E, Descriptor<E>>() {
            @Override
            public Descriptor<E> apply(E input) {
                return input == null ? null : builder.build(input);
            }
        });
    }
}
