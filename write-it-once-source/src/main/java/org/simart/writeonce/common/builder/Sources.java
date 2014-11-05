package org.simart.writeonce.common.builder;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.simart.writeonce.common.GeneratorRuntimeException;

import com.google.common.base.Preconditions;

public class Sources {
    public static CompilationUnit parse(String patch) {
        try {
            Preconditions.checkNotNull(patch);
            return JavaParser.parse(new FileInputStream(patch));
        } catch (FileNotFoundException e) {
            throw new GeneratorRuntimeException(e);
        } catch (ParseException e) {
            throw new GeneratorRuntimeException(e);
        }
    }
}
