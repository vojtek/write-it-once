package org.simart.writeonce.domain;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.simart.writeonce.common.AnnotationDescriptor;
import org.simart.writeonce.common.ClassDescriptor;
import org.simart.writeonce.common.FieldDescriptor;
import org.simart.writeonce.common.MethodDescriptor;
import org.simart.writeonce.common.PackageDescriptor;

public class AnnotationDescriptorImpl implements AnnotationDescriptor {

    // private final Context context;
    private final Annotation annotation;
    private final ClassDescriptor classDescriptor;

    public AnnotationDescriptorImpl(Context context, Annotation annotation) {
        super();
        // this.context = context;
        this.annotation = annotation;
        this.classDescriptor = context.create(ClassDescriptor.class, this.annotation.annotationType());
    }

    public String getName() {
        return classDescriptor.getName();
    }

    public String getShortName() {
        return classDescriptor.getShortName();
    }

    public PackageDescriptor getPackage() {
        return classDescriptor.getPackage();
    }

    public AnnotationDescriptor[] getAnnotations() {
        return classDescriptor.getAnnotations();
    }

    public Map<String, AnnotationDescriptor> getAnnotation() {
        return classDescriptor.getAnnotation();
    }

    public MethodDescriptor[] getMethods() {
        return classDescriptor.getMethods();
    }

    public Map<String, MethodDescriptor> getMethod() {
        return classDescriptor.getMethod();
    }

    public FieldDescriptor[] getFields() {
        return classDescriptor.getFields();
    }

    public Map<String, FieldDescriptor> getField() {
        return classDescriptor.getField();
    }

}
