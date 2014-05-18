package org.simart.writeonce.domain;

import org.simart.writeonce.common.DescriptorFactory;

public abstract class AbstractDescriptorFactory implements DescriptorFactory {

    private Context context;

    public void init(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

}
