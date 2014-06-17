package org.simart.writeonce.domain;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.simart.writeonce.common.AnnotationDescriptor;
import org.simart.writeonce.common.BeanClassDescriptor;
import org.simart.writeonce.common.BeanFieldDescriptor;
import org.simart.writeonce.common.BeanMethodDescriptor;
import org.simart.writeonce.common.ColumnDescriptor;
import org.simart.writeonce.common.EntityColumnDescriptor;
import org.simart.writeonce.common.EntityTableDescriptor;
import org.simart.writeonce.common.TypeDescriptor;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

public class FieldDescriptorImpl implements BeanFieldDescriptor, EntityColumnDescriptor {

    private final Context context;
    private final Field field;
    private final Function<Annotation, AnnotationDescriptor> annotation2descriptor;

    public FieldDescriptorImpl(final Context context, final Field field) {
	super();
	this.context = context;
	this.field = field;
	this.annotation2descriptor = new Function<Annotation, AnnotationDescriptor>() {
	    @Override
	    public AnnotationDescriptor apply(Annotation input) {
		return context.create(AnnotationDescriptor.class, input);
	    }
	};
    }

    @Override
    public AnnotationDescriptor[] getAnnotations() {
	final Set<Annotation> annotations = ImmutableSet.copyOf(this.field.getAnnotations());
	final Collection<AnnotationDescriptor> annotationDescriptors = Collections2.transform(annotations, annotation2descriptor);
	return annotationDescriptors.toArray(new AnnotationDescriptor[annotationDescriptors.size()]);
    }

    @Override
    public Map<String, AnnotationDescriptor> getAnnotation() {
	final AnnotationDescriptor[] annotations = getAnnotations();
	final Map<String, AnnotationDescriptor> result = Maps.newHashMap();
	for (AnnotationDescriptor annotation : annotations) {
	    result.put(annotation.getName(), annotation);
	}
	return result;
    }

    @Override
    public String getName() {
	return field.getName();
    }

    @Override
    public TypeDescriptor getType() {
	return context.create(TypeDescriptor.class, field.getType());
    }

    @Override
    public BeanMethodDescriptor getGetter() {
	final BeanClassDescriptor beanClassDescriptor = context.create(BeanClassDescriptor.class, field.getDeclaringClass());
	return beanClassDescriptor.getGetterByField().get(getName());
    }

    @Override
    public BeanMethodDescriptor getSetter() {
	final BeanClassDescriptor beanClassDescriptor = context.create(BeanClassDescriptor.class, field.getDeclaringClass());
	return beanClassDescriptor.getSetterByField().get(getName());
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((field == null) ? 0 : field.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	FieldDescriptorImpl other = (FieldDescriptorImpl) obj;
	if (field == null) {
	    if (other.field != null)
		return false;
	} else if (!field.equals(other.field))
	    return false;
	return true;
    }

    @Override
    public Boolean hasGetter() {
	return getGetter() != null;
    }

    @Override
    public Boolean hasSetter() {
	return getSetter() != null;
    }

    @Override
    public boolean isEntity() {
	return context.create(EntityTableDescriptor.class, this.field.getDeclaringClass()) != null;
    }

    @Override
    public ColumnDescriptor getColumn() {
	return context.create(ColumnDescriptor.class, this.field);
    }

}
