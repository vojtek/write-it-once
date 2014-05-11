package com.wk.simart.writeonce.domain;

import com.wk.simart.writeonce.common.DescriptorFactory;

public abstract class AbstractDescriptorFactory implements DescriptorFactory {

    private Context context;

    public void init(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

}
