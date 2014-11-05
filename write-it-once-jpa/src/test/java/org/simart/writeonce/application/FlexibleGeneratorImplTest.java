package org.simart.writeonce.application;

import static org.fest.assertions.Assertions.assertThat;

import org.simart.writeonce.common.GeneratorException;
import org.simart.writeonce.common.builder.JpaDescriptorConfigurator;
import org.simart.writeonce.domain.Atest;
import org.testng.annotations.Test;

public class FlexibleGeneratorImplTest {

    @Test
    public void table() throws GeneratorException {
        // given
        final FlexibleGenerator generator = FlexibleGenerator.create("${cls.table.name}");

        // when 
        final String result = generator.bind("cls", JpaDescriptorConfigurator.createClassDescriptorBuilder(), Atest.class).generate();

        // then
        assertThat(result).isEqualTo("A_TEST");
    }

    @Test
    public void columnByField() throws GeneratorException {
        // given
        final FlexibleGenerator generator = FlexibleGenerator.create("${cls.field['atestField'].column.name} ${cls.method['getBtest'].column.name}");

        // when 
        final String result = generator.bind("cls", JpaDescriptorConfigurator.createClassDescriptorBuilder(), Atest.class).generate();

        // then
        assertThat(result).isEqualTo("AT_EST B_TEST");
    }

    @Test
    public void columnByMethod() throws GeneratorException {
        // given
        final FlexibleGenerator generator = FlexibleGenerator.create("${cls.method['getAtestField'].column.name} ${cls.field['btest'].column.name}");

        // when 
        final String result = generator.bind("cls", JpaDescriptorConfigurator.createClassDescriptorBuilder(), Atest.class).generate();

        // then
        assertThat(result).isEqualTo("AT_EST B_TEST");
    }

}
