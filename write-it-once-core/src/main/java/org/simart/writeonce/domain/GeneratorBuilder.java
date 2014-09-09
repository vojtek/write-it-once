package org.simart.writeonce.domain;

import java.util.LinkedList;

import org.simart.writeonce.common.ColumnNameResolver;
import org.simart.writeonce.common.ColumnTypeResolver;
import org.simart.writeonce.common.DescriptorFactory;
import org.simart.writeonce.common.Generator;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

public class GeneratorBuilder {

    private static GeneratorBuilder empty() {
        return new GeneratorBuilder();
    }

    public static GeneratorBuilder instance() {
        return empty()
                .resolver(new DefaultColumnNameResolver())
                .add(new AnnotationFactory())
                .add(new FieldFactory())
                .add(new MethodFactory())
                .add(new ParameterFactory())
                .add(new PackageFactory())
                .add(new ColumnFactory())
                .add(new EntityFactory())
                .add(new TypeFactory())
                .add(new UtilsFactory());
    }

    private final LinkedList<DescriptorFactory> descriptorFactories = new LinkedList<DescriptorFactory>();
    private ColumnNameResolver columnNameResolver;
    private ColumnTypeResolver columnTypeResolver;
    private TableNameResolver tableNameResolver;
    private String mainAlias = "cls";

    public GeneratorBuilder override(DescriptorFactory descriptorFactory) {
        descriptorFactories.offerFirst(descriptorFactory);
        return this;
    }

    public GeneratorBuilder add(DescriptorFactory descriptorFactory) {
        descriptorFactories.add(descriptorFactory);
        return this;
    }

    public GeneratorBuilder resolver(ColumnNameResolver resolver) {
        this.columnNameResolver = resolver;
        return this;
    }

    public GeneratorBuilder resolver(ColumnTypeResolver resolver) {
        this.columnTypeResolver = resolver;
        return this;
    }

    public GeneratorBuilder resolver(TableNameResolver resolver) {
        this.tableNameResolver = resolver;
        return this;
    }

    public GeneratorBuilder mainAlias(String mainAlias) {
        Preconditions.checkNotNull(mainAlias);
        this.mainAlias = mainAlias;
        return this;
    }

    public Generator build() {
        final Context context = new Context();
        context.setDescriptorFactories(ImmutableList.copyOf(descriptorFactories));
        context.setColumnNameResolver(columnNameResolver);
        context.setColumnTypeResolver(columnTypeResolver);
        context.setTableNameResolver(tableNameResolver);
        final Generator generator = new GeneratorImpl(context, mainAlias);
        return generator;
    }
}
