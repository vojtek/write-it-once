package org.simart.writeonce.common;

/**
 * Enum descriptor. Access to static enum types.
 * 
 * @author Wojciech Kołodziej
 * 
 */
public interface EnumDescriptor {

    /**
     * <code>true</code> if class is enum
     * 
     * @return
     */
    Boolean isEnum();

    /**
     * Enum constraints
     * 
     * @return
     */
    Object[] getEnums();
}
