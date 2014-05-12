package org.simart.writeonce.application;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.reflections.Reflections;
import org.simart.writeonce.common.Generator;
import org.simart.writeonce.common.GeneratorException;
import org.simart.writeonce.domain.Builder;
import org.simart.writeonce.domain.GeneratorBuilder;
import org.simart.writeonce.utils.FileUtils;

public class GeneratorServiceTest {

    private static final String generatedFilePatch = "src\\generated\\java\\";

    public static void main(String[] args) throws GeneratorException, IOException {
        final Generator generator = GeneratorBuilder.instance().build();

        final String template = FileUtils.read("src/test/resources/scripts/Builder.java");

        final Reflections reflections = new Reflections("com.wk.simart.writeonce.domain");
        final Set<Class<?>> datas = reflections.getTypesAnnotatedWith(Builder.class);

        for (Class<?> data : datas) {
            final String sourceCode = generator.generate(data, template);
            final String fileName = generator.generate(data, "${cls.shortName}Builder.java");
            final String filePath = generatedFilePatch + FileUtils.package2Path(data) + File.separator + fileName;
            FileUtils.write(filePath, sourceCode);
        }

    }
}
