package org.simart.writeonce.application;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import org.reflections.Reflections;
import org.simart.writeonce.common.Action;
import org.simart.writeonce.common.GeneratorException;
import org.simart.writeonce.common.builder.ClassDescriptorBuilder;
import org.simart.writeonce.common.builder.DescriptorBuilder;
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

    @Test
    public void extend() throws GeneratorException {
        // given
        final FlexibleGenerator generator = FlexibleGenerator.create("XXX${cls.name} ${cls.someValue} ${cls.isEnum} ${cls.interfaces[0].name}");

        final DescriptorBuilder<Class<?>> descriptorBuilder = ClassDescriptorBuilder.create()
                .action("isEnum", new Action<Class<?>>() {
                    @Override
                    public Object execute(Class<?> data) {
                        return data.isEnum();
                    }
                })
                .action("interfaces", new Action<Class<?>>() {
                    @Override
                    public Object execute(Class<?> data) {
                        return DescriptorBuilders.build(ClassDescriptorBuilder.create(), Arrays.asList(data.getInterfaces()));
                    }
                })
                .value("someValue", "YYYY");

        // when
        generator.descriptor("cls", descriptorBuilder);

        // then
        assertThat(generator.bind("cls", Atest.class).generate()).isEqualTo("XXXorg.simart.writeonce.domain.Atest YYYY false java.io.Serializable");
    }
}
