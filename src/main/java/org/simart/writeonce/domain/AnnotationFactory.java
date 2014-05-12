package org.simart.writeonce.domain;

import java.lang.annotation.Annotation;

import org.simart.writeonce.common.AnnotationDescriptor;
import org.simart.writeonce.common.DescriptorFactory;

public class AnnotationFactory implements DescriptorFactory {

    private Context context;

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> cls, Object data) {
        return (AnnotationDescriptor.class.isAssignableFrom(cls) ? (T) new AnnotationDescriptorImpl(context, (Annotation) data) : null);
    }

    public void init(Context context) {
        this.context = context;
    }

}
