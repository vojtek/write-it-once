package org.simart.writeonce.common.builder;

import static org.fest.assertions.Assertions.assertThat;

import org.simart.writeonce.application.Generator;
import org.simart.writeonce.common.GeneratorException;
import org.simart.writeonce.domain.Atest;
import org.testng.annotations.Test;

public class AnnotationDescriptorBuilderTest {

    @Test
    public void type() throws GeneratorException {
	// given
	final Generator generator = Generator.create("${cls.annotation['org.simart.writeonce.domain.Builder'].name}");
	ReflectionPlugin.configure(generator);
	// when then
	assertThat(generator.bindBuilder("cls", Class.class).bindValue("cls", Atest.class).generate()).isEqualTo("org.simart.writeonce.domain.Builder");
    }

    @Test
    public void attribute() throws GeneratorException {
	// given
	final Generator generator = Generator.create("${cls.annotation['org.simart.writeonce.domain.Builder'].attribute.alias()}");
	ReflectionPlugin.configure(generator);
	// when then
	assertThat(generator.bindBuilder("cls", Class.class).bindValue("cls", Atest.class).generate()).isEqualTo("xxx");
    }

}
