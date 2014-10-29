package org.simart.writeonce.application;

import static org.fest.assertions.Assertions.assertThat;

import org.simart.writeonce.common.GeneratorException;
import org.simart.writeonce.common.builder.ClassDescriptorBuilder;
import org.simart.writeonce.domain.Atest;
import org.simart.writeonce.domain.FlexibleGenerator;
import org.testng.annotations.Test;

public class FlexibleGeneratorImplTest {

    @Test
    public void standardGeneration() throws GeneratorException {
        // given
        final FlexibleGenerator generator = FlexibleGenerator.create("XXX${cls.name}");

        // when 
        final String result = generator.bind("cls", ClassDescriptorBuilder.create(), Atest.class).generate();

        // then
        assertThat(result).isEqualTo("XXXorg.simart.writeonce.domain.Atest");
    }
}
