package org.simart.writeonce.common;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Database table descriptor based on JPA annotation
 * 
 * @see Entity
 * @see Table
 * @author Wojciech Kołodziej
 * 
 */
@Deprecated
public interface TableDescriptor extends ElementaryDescriptor {

    /**
     * Database schema name
     * 
     * @return
     */
    @Deprecated
    String getSchema();

    /**
     * Database table name
     * 
     * @return
     */
    @Deprecated
    String getName();

    /**
     * Table's primaty key - use this if one
     * 
     * @return
     */
    @Deprecated
    ColumnDescriptor getSinglePrimaryKey();

    /**
     * Table's column by name
     * 
     * @return
     */
    @Deprecated
    Map<String, ColumnDescriptor> getColumn();

    /**
     * All table's columns in array
     * 
     * @return
     */
    @Deprecated
    ColumnDescriptor[] getColumns();

}
