package org.simart.writeonce.common.builder;

import static org.fest.assertions.Assertions.assertThat;

import org.simart.writeonce.application.FlexibleGenerator;
import org.simart.writeonce.common.GeneratorException;
import org.simart.writeonce.domain.Atest;
import org.simart.writeonce.domain.Enumek;
import org.testng.annotations.Test;

public class ClassDescriptorBuilderTest {

    @Test
    public void name() {
	assertThat(ClassDescriptorBuilder.create().build(Atest.class, null).get("name")).isEqualTo(Atest.class.getName());
    }

    @Test
    public void method() throws NoSuchMethodException, SecurityException, GeneratorException {
	// given
	final FlexibleGenerator generator = FlexibleGenerator.create("${cls.method['getId'].name}");
	ReflectionPlugin.configure(generator);
	// when then
	assertThat(generator.bind("cls", Class.class).evaluate("cls", Atest.class).generate()).isEqualTo(Atest.class.getMethod("getId").getName());
    }

    @Test
    public void field() throws SecurityException, NoSuchFieldException, GeneratorException {
	// given
	final FlexibleGenerator generator = FlexibleGenerator.create("${cls.field['id'].name}");
	ReflectionPlugin.configure(generator);
	// when then
	assertThat(generator.bind("cls", Class.class).evaluate("cls", Atest.class).generate()).isEqualTo(Atest.class.getDeclaredField("id").getName());
    }

    @Test
    public void annotation() throws SecurityException, NoSuchFieldException, GeneratorException {
	// given
	final FlexibleGenerator generator = FlexibleGenerator.create("${cls.annotation['org.simart.writeonce.domain.Describe'].attribute.value()}");
	ReflectionPlugin.configure(generator);
	// when then
	assertThat(generator.bind("cls", Class.class).evaluate("cls", Atest.class).generate()).isEqualTo("Annotation DSC");
    }

    @Test
    public void enumeration() throws SecurityException, NoSuchFieldException {
	final Enumek[] values = (Enumek[]) ClassDescriptorBuilder.create().build(Enumek.class, null).get("enums");
	assertThat(values).containsOnly(Enumek.OPTION_1, Enumek.OPTION_2);
    }

}
