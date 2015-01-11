package org.simart.writeonce.application;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import org.reflections.Reflections;
import org.simart.writeonce.common.Action;
import org.simart.writeonce.common.GeneratorException;
import org.simart.writeonce.common.builder.DescriptorBuilders;
import org.simart.writeonce.common.builder.ReflectionPlugin;
import org.simart.writeonce.domain.Atest;
import org.simart.writeonce.domain.Builder;
import org.simart.writeonce.utils.FileUtils;
import org.testng.annotations.Test;

public class FlexibleGeneratorImplTest {

    @Test
    public void standard() throws GeneratorException {
	// given
	final FlexibleGenerator generator = FlexibleGenerator.create("XXX${cls.name}");
	ReflectionPlugin.configure(generator);

	// when
	final String result = generator.evaluate("cls", Class.class, Atest.class).generate();

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
	ReflectionPlugin.configure(generator);

	generator.bind("cls", Class.class);

	for (Class<?> data : datas) {
	    // when
	    final String sourceCode = generator.evaluate("cls", data).generate();
	    final String fileName = generator.generate("${cls.package.path}${cls.shortName}Builder.java");
	    final String filePath = generatedFilePatch + fileName;

	    // then
	    FileUtils.write(filePath, sourceCode);
	}
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void extend() throws GeneratorException {
	// given
	final FlexibleGenerator generator = FlexibleGenerator.create("XXX${cls.name} ${cls.someValue} ${cls.isEnum} ${cls.interfaces[0].name}");
	ReflectionPlugin.configure(generator);

	generator.getContext().getBuilder(Class.class)
		.action("isEnum", new Action<Class>() {
		    @Override
		    public Object execute(Class data, Context context) {
			return data.isEnum();
		    }
		})
		.action("interfaces", new Action<Class>() {
		    @Override
		    public Object execute(Class data, Context context) {
			return DescriptorBuilders.build(context.getBuilder(Class.class), Arrays.asList(data.getInterfaces()), context);
		    }
		})
		.value("someValue", "YYYY");

	// when
	generator.bind("cls", Class.class);

	// then
	assertThat(generator.evaluate("cls", Atest.class).generate()).isEqualTo("XXXorg.simart.writeonce.domain.Atest YYYY false java.io.Serializable");
    }
}
