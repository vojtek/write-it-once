package org.simart.writeonce.common.builder;

import static org.fest.assertions.Assertions.assertThat;
import japa.parser.ast.CompilationUnit;

import java.io.File;

import org.simart.writeonce.application.FlexibleGenerator;
import org.simart.writeonce.common.GeneratorException;
import org.simart.writeonce.common.SourcePath;
import org.simart.writeonce.domain.Atest;
import org.simart.writeonce.domain.Btest;
import org.testng.annotations.Test;

public class SourceDescriptorBuilderTest {

    private static final SourcePath SOURCE_PATH = SourcePath.create("src" + File.separator + "test" + File.separator + "java" + File.separator);

    @Test
    public void comment() {
	// given
	final CompilationUnit compilationUnit = Sources.parse(SOURCE_PATH, Atest.class);
	// when
	final String comment = (String) SourceClassDescriptorBuilder.create().build(SourceClassDescriptorBuilder.getClassNode(compilationUnit, Atest.class), null).get("comment");
	// then
	assertThat(comment).contains("Javadoc class comment");
    }

    @Test
    public void noComment() {
	// given
	final CompilationUnit compilationUnit = Sources.parse(SOURCE_PATH, Btest.class);
	// when
	final String comment = (String) SourceClassDescriptorBuilder.create().build(SourceClassDescriptorBuilder.getClassNode(compilationUnit, Btest.class), null).get("comment");
	// then
	assertThat(comment).isNull();
    }

    @Test
    public void fullGeneratorTest() throws GeneratorException {
	// given
	final FlexibleGenerator generator = FlexibleGenerator.create("${cls.name} -> ${cls.comment}").lineSeparator("\n");
	SourcePlugin.configure(generator, SOURCE_PATH);
	// when
	final String result = generator.bind("cls", Class.class).evaluate("cls", Atest.class).generate();
	// then
	assertThat(result).isEqualTo("org.simart.writeonce.domain.Atest -> \n * Javadoc class comment\n * \n * @author Wojtek\n *\n ");
    }

}
