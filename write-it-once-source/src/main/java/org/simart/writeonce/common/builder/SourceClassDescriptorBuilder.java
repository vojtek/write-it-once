package org.simart.writeonce.common.builder;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.Node;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;

import java.util.Iterator;

import org.simart.writeonce.common.Action;

public class SourceClassDescriptorBuilder extends DefaultDescriptorBuilder<ClassOrInterfaceDeclaration> {

    public static SourceClassDescriptorBuilder create() {
        final SourceClassDescriptorBuilder builder = new SourceClassDescriptorBuilder();

        builder.action("javadoc", new Action<ClassOrInterfaceDeclaration>() {
            @Override
            public Object execute(ClassOrInterfaceDeclaration data) {
                return data.getJavaDoc() != null ? data.getJavaDoc().getContent() : null;
            }
        });

        builder.action("comment", new Action<ClassOrInterfaceDeclaration>() {
            @Override
            public Object execute(ClassOrInterfaceDeclaration data) {
                return data.getComment() != null ? data.getComment().getContent() : null;
            }
        });

        return builder;
    }

    public static ClassOrInterfaceDeclaration getClassNode(CompilationUnit cu, Class<?> type) {
        final Iterator<Node> nodeIt = cu.getChildrenNodes().iterator();
        while (nodeIt.hasNext()) {
            final Node node = nodeIt.next();
            if (node instanceof ClassOrInterfaceDeclaration) {
                final ClassOrInterfaceDeclaration classOrInterfaceDeclaration = (ClassOrInterfaceDeclaration) node;
                if (classOrInterfaceDeclaration.getName().equals(type.getSimpleName())) {
                    return classOrInterfaceDeclaration;
                }
            }
        }
        return null;
    }

}
