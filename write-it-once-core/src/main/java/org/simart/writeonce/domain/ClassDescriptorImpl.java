package org.simart.writeonce.domain;

import static org.reflections.ReflectionUtils.getAllAnnotations;
import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.getAllMethods;
import static org.reflections.ReflectionUtils.withModifier;
import static org.reflections.ReflectionUtils.withPrefix;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;

import org.simart.writeonce.common.AnnotationDescriptor;
import org.simart.writeonce.common.BeanClassDescriptor;
import org.simart.writeonce.common.BeanMethodDescriptor;
import org.simart.writeonce.common.ClassDescriptor;
import org.simart.writeonce.common.EntityTableDescriptor;
import org.simart.writeonce.common.EnumDescriptor;
import org.simart.writeonce.common.FieldDescriptor;
import org.simart.writeonce.common.MethodDescriptor;
import org.simart.writeonce.common.TableDescriptor;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class ClassDescriptorImpl extends TypeDescriptorImpl implements ClassDescriptor, BeanClassDescriptor, EntityTableDescriptor, EnumDescriptor {

    private final Function<Method, BeanMethodDescriptor> method2descriptor;
    private final Function<Field, FieldDescriptor> field2descriptor;
    private final Function<Annotation, AnnotationDescriptor> annotation2descriptor;

    public ClassDescriptorImpl(final Context context, final Class<?> cls) {
        super(context, cls);
        this.method2descriptor = new Function<Method, BeanMethodDescriptor>() {

            @Override
            public BeanMethodDescriptor apply(Method input) {
                return context.create(BeanMethodDescriptor.class, input);
            }
        };
        this.field2descriptor = new Function<Field, FieldDescriptor>() {

            @Override
            public FieldDescriptor apply(Field input) {
                return context.create(FieldDescriptor.class, input);
            }
        };
        this.annotation2descriptor = new Function<Annotation, AnnotationDescriptor>() {

            @Override
            public AnnotationDescriptor apply(Annotation input) {
                return context.create(AnnotationDescriptor.class, input);
            }
        };
    }

    @Override
    public String getName() {
        return cls.getName();
    }

    @Override
    public String getShortName() {
        return cls.getSimpleName();
    }

    @Override
    public MethodDescriptor[] getMethods() {
        @SuppressWarnings("unchecked")
        final Set<Method> methods = getAllMethods(this.cls);
        return Collections2.transform(methods, method2descriptor).toArray(new MethodDescriptor[methods.size()]);
    }

    @Override
    public Map<String, MethodDescriptor> getMethod() {
        final Set<Method> methods = ImmutableSet.copyOf(cls.getMethods());
        final Map<String, MethodDescriptor> result = Maps.newHashMap();
        for (Method method : methods) {
            result.put(method.getName(), method2descriptor.apply(method));
        }
        return result;
    }

    @Override
    public FieldDescriptor[] getFields() {
        @SuppressWarnings("unchecked")
        final Set<Field> fields = Sets.difference(getAllFields(cls), getAllFields(cls, withModifier(Modifier.STATIC)));
        return Collections2.transform(fields, field2descriptor).toArray(new FieldDescriptor[fields.size()]);
    }

    @Override
    public Map<String, FieldDescriptor> getField() {
        @SuppressWarnings("unchecked")
        final Set<Field> fields = getAllFields(this.cls);
        final Map<String, FieldDescriptor> result = Maps.newHashMap();
        for (Field field : fields) {
            result.put(field.getName(), field2descriptor.apply(field));
        }
        return result;
    }

    @Override
    public BeanMethodDescriptor[] getGetters() {
        @SuppressWarnings("unchecked")
        final Set<Method> methods = getAllMethods(this.cls, withPrefix("get"));
        return Collections2.transform(methods, method2descriptor).toArray(new BeanMethodDescriptor[methods.size()]);
    }

    @Override
    public Map<String, BeanMethodDescriptor> getGetter() {
        final BeanMethodDescriptor[] methods = getGetters();
        final Map<String, BeanMethodDescriptor> result = Maps.newHashMap();
        for (BeanMethodDescriptor method : methods) {
            result.put(method.getName(), method);
        }
        return result;
    }

    @Override
    public BeanMethodDescriptor[] getSetters() {
        @SuppressWarnings("unchecked")
        final Set<Method> methods = getAllMethods(this.cls, withPrefix("set"));
        return Collections2.transform(methods, method2descriptor).toArray(new BeanMethodDescriptor[methods.size()]);
    }

    @Override
    public Map<String, BeanMethodDescriptor> getSetter() {
        final BeanMethodDescriptor[] methods = getSetters();
        final Map<String, BeanMethodDescriptor> result = Maps.newHashMap();
        for (BeanMethodDescriptor method : methods) {
            result.put(method.getName(), method);
        }
        return result;
    }

    @Override
    public Map<String, BeanMethodDescriptor> getGetterByField() {
        final BeanMethodDescriptor[] methods = getGetters();
        final Map<String, BeanMethodDescriptor> result = Maps.newHashMap();
        for (BeanMethodDescriptor method : methods) {
            final FieldDescriptor fieldDescriptor = method.getField();
            if (fieldDescriptor == null) {
                continue;
            }
            result.put(fieldDescriptor.getName(), method);
        }
        return result;
    }

    @Override
    public Map<String, BeanMethodDescriptor> getSetterByField() {
        final BeanMethodDescriptor[] methods = getSetters();
        final Map<String, BeanMethodDescriptor> result = Maps.newHashMap();
        for (BeanMethodDescriptor method : methods) {
            final FieldDescriptor fieldDescriptor = method.getField();
            if (fieldDescriptor == null) {
                continue;
            }
            result.put(fieldDescriptor.getName(), method);
        }
        return result;
    }

    @Override
    public TableDescriptor getTable() {
        return context.create(TableDescriptor.class, cls);
    }

    @Override
    @SuppressWarnings("unchecked")
    public AnnotationDescriptor[] getAnnotations() {
        final Set<Annotation> annotations = getAllAnnotations(cls);
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
    public Map<String, FieldDescriptor[]> getFieldsByAnnotation() {
        final FieldDescriptor[] fieldDescriptors = getFields();
        final Map<String, Set<FieldDescriptor>> data = Maps.newHashMap();
        for (FieldDescriptor fieldDescriptor : fieldDescriptors) {
            for (AnnotationDescriptor annotationDescriptor : fieldDescriptor.getAnnotations()) {
                Set<FieldDescriptor> subData = data.get(annotationDescriptor.getName());
                if (subData == null) {
                    subData = Sets.newHashSet();
                    data.put(annotationDescriptor.getName(), subData);
                }
                subData.add(fieldDescriptor);
            }
        }
        final Map<String, FieldDescriptor[]> result = Maps.asMap(data.keySet(), new Function<String, FieldDescriptor[]>() {
            @Override
            public FieldDescriptor[] apply(String input) {
                return data.get(input).toArray(new FieldDescriptor[data.get(input).size()]);
            }
        });
        return result;
    }

    @Override
    public Map<String, MethodDescriptor[]> getMethodsByAnnotation() {
        final MethodDescriptor[] descriptors = getMethods();
        final Map<String, Set<MethodDescriptor>> data = Maps.newHashMap();
        for (MethodDescriptor descriptor : descriptors) {
            for (AnnotationDescriptor annotationDescriptor : descriptor.getAnnotations()) {
                Set<MethodDescriptor> subData = data.get(annotationDescriptor.getName());
                if (subData == null) {
                    subData = Sets.newHashSet();
                    data.put(annotationDescriptor.getName(), subData);
                }
                subData.add(descriptor);
            }
        }
        final Map<String, MethodDescriptor[]> result = Maps.asMap(data.keySet(), new Function<String, MethodDescriptor[]>() {
            @Override
            public MethodDescriptor[] apply(String input) {
                return data.get(input).toArray(new MethodDescriptor[data.get(input).size()]);
            }
        });
        return result;
    }

    @Override
    public Boolean isEntity() {
        return getAnnotation().get(Entity.class.getName()) != null;
    }

    @Override
    public Boolean isEnum() {
        return cls.getEnumConstants() != null;
    }

    @Override
    public Object[] getEnums() {
        return cls.getEnumConstants();
    }

}
