package org.simart.writeonce.domain;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Set;

import org.reflections.ReflectionUtils;
import org.simart.writeonce.common.ElementaryDescriptor;
import org.simart.writeonce.common.Help;

import com.google.common.collect.Lists;

public class HelpFactory {

    public Help create(ElementaryDescriptor descriptor) {
        return new HelpImpl(descriptor);
    }

    private class HelpImpl implements Help {
        private final ElementaryDescriptor descriptor;

        HelpImpl(ElementaryDescriptor descriptor) {
            super();
            this.descriptor = descriptor;
        }

        @Override
        public String getRootType() {
            return descriptor.get_root().getClass().getName();
        }

        @Override
        public String getBeanDescriptor() {
            return descriptor.getClass().getName();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Collection<String> getMethods() {
            final Set<Method> methods = ReflectionUtils.getAllMethods(descriptor.getClass(), ReflectionUtils.withModifier(Modifier.PUBLIC), ReflectionUtils.withParameters());
            final Collection<String> results = Lists.newArrayList();
            for (Method method : methods) {
                results.add(method.toString());
            }
            return results;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append(getBeanDescriptor()).append('(').append(getRootType()).append(')');
            builder.append('{');
            for (String method : getMethods()) {
                builder.append(method).append("; ");
            }
            builder.append('}');
            return builder.toString();
        }

    }
}
