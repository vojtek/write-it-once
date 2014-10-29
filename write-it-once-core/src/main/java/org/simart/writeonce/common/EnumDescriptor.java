package org.simart.writeonce.common;

/**
 * Enum descriptor. Access to static enum types.
 * 
 * @author Wojciech Kołodziej
 * 
 */
@Deprecated
public interface EnumDescriptor extends ElementaryDescriptor {

    /**
     * <code>true</code> if class is enum
     * 
     * @return
     */
    @Deprecated
    Boolean isEnum();

    /**
     * Enum constraints
     * 
     * @return
     */
    @Deprecated
    Object[] getEnums();
}
