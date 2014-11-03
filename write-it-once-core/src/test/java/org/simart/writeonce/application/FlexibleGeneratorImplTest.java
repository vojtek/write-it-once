package org.simart.writeonce.application;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;
import java.util.Set;

import org.reflections.Reflections;
import org.simart.writeonce.common.GeneratorException;
import org.simart.writeonce.common.builder.ClassDescriptorBuilder;
import org.simart.writeonce.common.builder.DescriptorBuilders;
import org.simart.writeonce.domain.Atest;
import org.simart.writeonce.domain.Builder;
import org.simart.writeonce.utils.FileUtils;
import org.testng.annotations.Test;

public class FlexibleGeneratorImplTest {

    @Test
    public void standard() throws GeneratorException {
        // given
        final FlexibleGenerator generator = FlexibleGenerator.create("XXX${cls.name}");

        // when 
        final String result = generator.bind("cls", ClassDescriptorBuilder.create(), Atest.class).generate();

        // then
        assertThat(result).isEqualTo("XXXorg.simart.writeonce.domain.Atest");
    }

    @Test
    public void template() throws IOException, GeneratorException {
        // given 
        final String generatedFilePatch = "src\\generated\\java\\";
        final Reflections reflections = new Reflections("org.simart.writeonce.domain");
        final Set<Class<?>> datas = reflections.getTypesAnnotatedWith(Builder.class);

        final String template = FileUtils.read("src\\test\\resources\\scripts\\Builder.java");
        final FlexibleGenerator generator = FlexibleGenerator.create(template);

        generator.descriptor("cls", DescriptorBuilders.createClassDescriptorBuilder());

        for (Class<?> data : datas) {
            // when
            final String sourceCode = generator.bind("cls", data).generate();
            final String fileName = generator.generate("${cls.package.path}${cls.shortName}Builder.java");
            final String filePath = generatedFilePatch + fileName;

            // then
            FileUtils.write(filePath, sourceCode);
        }
    }
}
