package org.simart.writeonce.common.builder;

import java.util.List;

import org.simart.writeonce.application.Context;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class DescriptorBuilders {

    public static <E> List<Descriptor<E>> build(final DescriptorBuilder<E> builder, final List<E> datas, final Context context) {
        return Lists.transform(datas, new Function<E, Descriptor<E>>() {
            @Override
            public Descriptor<E> apply(E input) {
                return input == null ? null : builder.build(input, context);
            }
        });
    }
}
