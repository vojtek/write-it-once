package org.simart.writeonce.common.builder;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.simart.writeonce.common.GeneratorRuntimeException;
import org.simart.writeonce.common.SourcePath;

import com.google.common.base.Preconditions;

public class Sources {

    public static String getPatch(SourcePath sourcePath, Class<?> cls) {
	final String[] parts = cls.getName().split("\\.");
	final StringBuilder result = new StringBuilder();
	result.append(sourcePath.path);
	for (int i = 0; i < parts.length; i++) {
	    result.append(parts[i]);
	    if (i + 1 != parts.length) {
		result.append(File.separator);
	    }
	}
	result.append(".java");
	return result.toString();
    }

    public static CompilationUnit parse(String fullPath) {
	try {
	    Preconditions.checkNotNull(fullPath);
	    return JavaParser.parse(new FileInputStream(fullPath));
	} catch (FileNotFoundException e) {
	    throw new GeneratorRuntimeException(e);
	} catch (ParseException e) {
	    throw new GeneratorRuntimeException(e);
	}
    }

    public static CompilationUnit parse(SourcePath sourcePath, Class<?> cls) {
	return parse(getPatch(sourcePath, cls));
    }
}
