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
public interface TableDescriptor {

    /** Database schema name **/
    String getSchema();

    /** Database table name */
    String getName();

    /**
     * Table's primaty keys
     * 
     * @see TableDescriptor#getSinglePrimaryKey()
     **/
    ColumnDescriptor[] getPrimaryKeys();

    /** Table's primaty key - use this if one */
    ColumnDescriptor getSinglePrimaryKey();

    /** Table's column by name */
    Map<String, ColumnDescriptor> getColumn();

    /** All table's columns in array */
    ColumnDescriptor[] getColumns();

}
