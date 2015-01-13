package org.simart.writeonce.common.builder;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;

import org.simart.writeonce.application.Context;
import org.simart.writeonce.application.Generator;
import org.simart.writeonce.common.Action;
import org.simart.writeonce.common.SourcePath;

public class SourcePlugin {

    @SuppressWarnings("rawtypes")
    public static void configure(Generator generator, SourcePath sourcePath) {
	ReflectionPlugin.configure(generator);

	generator.register(ClassOrInterfaceDeclaration.class, SourceClassDescriptorBuilder.create());
	generator.setHelper(sourcePath);

	generator.getBuilder(Class.class)
		.action("comment", new Action<Class>() {
		    @Override
		    public Object execute(Class data, Context context) {
			final SourcePath sourcePath = context.getHelper(SourcePath.class);
			final CompilationUnit compilationUnit = Sources.parse(sourcePath, data);
			final Descriptor<ClassOrInterfaceDeclaration> sourceDescriptor =
				context.getBuilder(ClassOrInterfaceDeclaration.class)
					.build(SourceClassDescriptorBuilder.getClassNode(compilationUnit, data), context);
			return sourceDescriptor.get("comment");
		    }
		});
    }
}
