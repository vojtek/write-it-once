package org.simart.writeonce.domain;

import java.util.List;

import org.simart.writeonce.common.ColumnNameResolver;
import org.simart.writeonce.common.ColumnTypeResolver;
import org.simart.writeonce.common.DescriptorFactory;
import org.simart.writeonce.common.TableNameResolver;

import com.google.common.collect.ImmutableList;

public class Context {

    private List<DescriptorFactory> descriptorFactories;
    private TableNameResolver tableNameResolver;
    private ColumnNameResolver columnNameResolver;
    private ColumnTypeResolver columnTypeResolver;

    public <T> T create(Class<T> cls, Object data) {
        for (DescriptorFactory descriptorFactory : this.descriptorFactories) {
            final T result = descriptorFactory.create(cls, data);
            if (result != null) {
                return result;
            }
        }
        throw new UnsupportedOperationException(String.format("unsupported class", cls.getName()));
    }

    void setDescriptorFactories(List<DescriptorFactory> descriptorFactories) {
        for (DescriptorFactory descriptorFactory : descriptorFactories) {
            descriptorFactory.init(this);
        }
        this.descriptorFactories = ImmutableList.copyOf(descriptorFactories);
    }

    public TableNameResolver getTableNameResolver() {
        return tableNameResolver;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public ColumnTypeResolver getColumnTypeResolver() {
        return columnTypeResolver;
    }

    void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    void setColumnTypeResolver(ColumnTypeResolver columnTypeResolver) {
        this.columnTypeResolver = columnTypeResolver;
    }

    void setTableNameResolver(TableNameResolver tableNameResolver) {
        this.tableNameResolver = tableNameResolver;
    }

}
