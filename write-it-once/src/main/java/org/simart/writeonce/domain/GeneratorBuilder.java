package org.simart.writeonce.domain;

import java.util.LinkedList;

import org.simart.writeonce.common.ColumnNameResolver;
import org.simart.writeonce.common.ColumnTypeResolver;
import org.simart.writeonce.common.DescriptorFactory;
import org.simart.writeonce.common.Generator;
import org.simart.writeonce.common.TableNameResolver;

import com.google.common.collect.ImmutableList;

public class GeneratorBuilder {

    private static GeneratorBuilder empty() {
        return new GeneratorBuilder();
    }

    public static GeneratorBuilder instance() {
        return empty()
                .add(new TypeFactory())
                .add(new FieldFactory())
                .add(new MethodFactory())
                .add(new ParameterFactory())
                .add(new PackageFactory())
                .add(new AnnotationFactory())
                .add(new ColumnFactory())
                .add(new TableFactory());
    }

    private final LinkedList<DescriptorFactory> descriptorFactories = new LinkedList<DescriptorFactory>();
    private ColumnNameResolver columnNameResolver;
    private ColumnTypeResolver columnTypeResolver;
    private TableNameResolver tableNameResolver;

    public GeneratorBuilder override(DescriptorFactory descriptorFactory) {
        descriptorFactories.offerFirst(descriptorFactory);
        return this;
    }

    public GeneratorBuilder add(DescriptorFactory descriptorFactory) {
        descriptorFactories.add(descriptorFactory);
        return this;
    }

    public GeneratorBuilder setResolver(ColumnNameResolver resolver) {
        this.columnNameResolver = resolver;
        return this;
    }

    public GeneratorBuilder setResolver(ColumnTypeResolver resolver) {
        this.columnTypeResolver = resolver;
        return this;
    }

    public GeneratorBuilder setResolver(TableNameResolver resolver) {
        this.tableNameResolver = resolver;
        return this;
    }

    public Generator build() {
        final Context context = new Context();
        context.setDescriptorFactories(ImmutableList.copyOf(descriptorFactories));
        context.setColumnNameResolver(columnNameResolver);
        context.setColumnTypeResolver(columnTypeResolver);
        context.setTableNameResolver(tableNameResolver);
        final Generator generator = new GeneratorImpl(context);
        return generator;
    }
}
