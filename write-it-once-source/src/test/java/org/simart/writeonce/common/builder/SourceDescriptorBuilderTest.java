package org.simart.writeonce.common.builder;

import static org.fest.assertions.Assertions.assertThat;
import japa.parser.ast.CompilationUnit;

import java.io.File;

import org.simart.writeonce.domain.Atest;
import org.simart.writeonce.domain.Btest;
import org.testng.annotations.Test;

public class SourceDescriptorBuilderTest {

    @Test
    public void comment() {
        // given
        final CompilationUnit compilationUnit = Sources.parse(generatePatch(Atest.class));
        //when
        final String comment = (String) SourceClassDescriptorBuilder.create().build(SourceClassDescriptorBuilder.getClassNode(compilationUnit, Atest.class)).get("comment");
        //then
        assertThat(comment).contains("Javadoc class comment");
    }

    @Test
    public void noComment() {
        // given
        final CompilationUnit compilationUnit = Sources.parse(generatePatch(Btest.class));
        //when
        final String comment = (String) SourceClassDescriptorBuilder.create().build(SourceClassDescriptorBuilder.getClassNode(compilationUnit, Btest.class)).get("comment");
        //then
        assertThat(comment).isNull();
    }

    private String generatePatch(Class<?> cls) {
        final String[] parts = cls.getName().split("\\.");
        final StringBuilder result = new StringBuilder();
        result.append("src" + File.separator + "test" + File.separator + "java" + File.separator);
        for (int i = 0; i < parts.length; i++) {
            result.append(parts[i]);
            if (i + 1 != parts.length) {
                result.append(File.separator);
            }
        }
        result.append(".java");
        return result.toString();
    }
}
