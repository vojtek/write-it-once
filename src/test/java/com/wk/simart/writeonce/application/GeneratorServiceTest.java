package com.wk.simart.writeonce.application;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.reflections.Reflections;

import com.wk.simart.writeonce.common.Generator;
import com.wk.simart.writeonce.common.GeneratorException;
import com.wk.simart.writeonce.domain.Builder;
import com.wk.simart.writeonce.domain.GeneratorBuilder;
import com.wk.simart.writeonce.utils.FileUtils;

public class GeneratorServiceTest {

    private static final String generatedFilePatch = "src\\generated\\java\\";

    public static void main(String[] args) throws GeneratorException, IOException {
        final Generator generator = GeneratorBuilder.instance().build();

        final String template = FileUtils.read("src/test/resources/scripts/Builder.java");

        Reflections reflections = new Reflections("com.wk.simart.writeonce.domain");
        final Set<Class<?>> datas = reflections.getTypesAnnotatedWith(Builder.class);

        for (Class<?> data : datas) {
            final String sourceCode = generator.generate(data, template);
            final String fileName = generator.generate(data, "${cls.shortName}Builder.java");
            final String filePatch = generatedFilePatch + FileUtils.package2Path(data) + File.separator + fileName;
            FileUtils.write(filePatch, sourceCode);
        }

    }
}
