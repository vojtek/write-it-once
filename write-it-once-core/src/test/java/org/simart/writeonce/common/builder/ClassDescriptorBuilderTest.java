package org.simart.writeonce.common.builder;

import static org.fest.assertions.Assertions.assertThat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.simart.writeonce.common.Descriptor;
import org.simart.writeonce.domain.Atest;
import org.simart.writeonce.domain.Enumek;
import org.testng.annotations.Test;

public class ClassDescriptorBuilderTest {

    @Test
    public void name() {
        assertThat(ClassDescriptorBuilder.create().build(Atest.class).get("name")).isEqualTo(Atest.class.getName());
    }

    @Test
    public void method() throws NoSuchMethodException, SecurityException {
        @SuppressWarnings("unchecked")
        final Map<String, Descriptor<Method>> descriptors = (Map<String, Descriptor<Method>>) ClassDescriptorBuilder.create().build(Atest.class).get("method");
        assertThat(descriptors.get("getId").get("name")).isEqualTo(Atest.class.getMethod("getId").getName());
    }

    @Test
    public void field() throws SecurityException, NoSuchFieldException {
        @SuppressWarnings("unchecked")
        final Map<String, Descriptor<Field>> descriptors = (Map<String, Descriptor<Field>>) ClassDescriptorBuilder.create().build(Atest.class).get("field");
        assertThat(descriptors.get("id").get("name")).isEqualTo(Atest.class.getDeclaredField("id").getName());
    }

    @Test
    public void annotation() throws SecurityException, NoSuchFieldException {
        @SuppressWarnings("unchecked")
        final Map<String, Descriptor<Field>> descriptors = (Map<String, Descriptor<Field>>) ClassDescriptorBuilder.create().build(Atest.class).get("field");
        assertThat(descriptors.get("id").get("name")).isEqualTo(Atest.class.getDeclaredField("id").getName());
    }

    @Test
    public void enumeration() throws SecurityException, NoSuchFieldException {
        final Enumek[] values = (Enumek[]) ClassDescriptorBuilder.create().build(Enumek.class).get("enums");
        assertThat(values).containsOnly(Enumek.OPTION_1, Enumek.OPTION_2);
    }
}
