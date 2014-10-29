package org.simart.writeonce.common;

import static org.simart.writeonce.common.builder.DescriptorBuilders.build;

import java.util.List;

public class Actions {

    public static Action collection(final DescriptorBuilder builder, final List<? extends Object> datas) {
        return new Action() {
            @Override
            public Object execute(final Object data) {
                return build(builder, datas);
            }
        };
    }
}
