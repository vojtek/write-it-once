package org.simart.writeonce.application;


public class TextClassDescriptorGenerator {
    /*
    private static final String generatedFilePatch = "src\\generated\\resources\\";

    public static void main(String[] args) throws GeneratorException, IOException {
        final Generator generator = GeneratorBuilder.instance()
                .resolver(new OracleSqlTypeResolver())
                .build();

        final String template = FileUtils.read("src/test/resources/scripts/TextClassDescriptor.txt");

        final Reflections reflections = new Reflections("org.simart.writeonce.domain");
        final Set<Class<?>> datas = reflections.getTypesAnnotatedWith(Describe.class);

        for (Class<?> data : datas) {
            final String sourceCode = generator.generate(data, template);
            final String fileName = generator.generate(data, "${cls.shortName}Descriptor.txt");
            final String filePath = generatedFilePatch + FileUtils.package2Path(data) + File.separator + fileName;
            FileUtils.write(filePath, sourceCode);
        }

    }
    */
}
