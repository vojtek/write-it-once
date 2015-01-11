package org.simart.writeonce.common.builder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.simart.writeonce.application.Context;
import org.simart.writeonce.common.Action;

import com.google.common.collect.Maps;

public class Descriptors {

    public static <E extends Object> Descriptor<E> newDescriptor(E data, Map<String, Object> content, Context context) {
        return new DefaultDescriptor<E>(data, content, context);
    }

    public static <E> Map<String, Descriptor<E>> extract(String field, List<Descriptor<E>> descriptors) {
        final Map<String, Descriptor<E>> result = Maps.newHashMap();
        for (Descriptor<E> descriptor : descriptors) {
            final Object value = descriptor.get(field);
            if (value instanceof String) {
                result.put((String) value, descriptor);
            }
        }
        return result;
    }

    public static class DefaultDescriptor<E extends Object> implements Descriptor<E> {
        private final E data;
        private final Context context;
        private final Map<String, Object> content = Maps.newHashMap();

        public DefaultDescriptor(E data, Map<String, Object> content, Context context) {
            super();
            this.content.putAll(content);
            this.data = data;
            this.context = context;
        }

        @Override
        public int size() {
            return content.size();
        }

        @Override
        public boolean isEmpty() {
            return content.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            return content.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            throw new UnsupportedOperationException("unable to check value contaiment: " + value);
        }

        @Override
        public Object get(Object key) {
            final Object value = content.get(key);
            if (value instanceof Action) {
                @SuppressWarnings("unchecked")
                final Action<E> action = (Action<E>) value;
                return action.execute(data, context);
            }
            return value;
        }

        @Override
        public Object put(String key, Object value) {
            return content.put(key, value);
        }

        @Override
        public Object remove(Object key) {
            return content.remove(key);
        }

        @Override
        public void putAll(Map<? extends String, ? extends Object> m) {
            content.putAll(m);
        }

        @Override
        public void clear() {
            content.clear();
        }

        @Override
        public Set<String> keySet() {
            return content.keySet();
        }

        @Override
        public Collection<Object> values() {
            return content.values();
        }

        @Override
        public Set<java.util.Map.Entry<String, Object>> entrySet() {
            return content.entrySet();
        }

        @Override
        public E getData() {
            return data;
        }

    }
}
