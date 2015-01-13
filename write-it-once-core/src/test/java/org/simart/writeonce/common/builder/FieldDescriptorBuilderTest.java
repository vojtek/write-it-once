package org.simart.writeonce.common.builder;

import static org.fest.assertions.Assertions.assertThat;

import org.simart.writeonce.application.Generator;
import org.simart.writeonce.common.GeneratorException;
import org.simart.writeonce.domain.Atest;
import org.testng.annotations.Test;

public class FieldDescriptorBuilderTest {

    @Test
    public void staticFieldValue() throws GeneratorException {
	// given
	final Generator generator = Generator.create("${cls.staticField['DATA'].value}").bindBuilder("cls", Class.class);
	ReflectionPlugin.configure(generator);

	// when
	final String result = generator.bindValue("cls", Atest.class).generate();
	// then
	assertThat(result).isEqualTo("Static data");
    }
}
