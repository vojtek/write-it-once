package org.simart.writeonce.common;

public class SourcePath {

    public static final SourcePath create(String path) {
	return new SourcePath(path);
    }

    public final String path;

    public SourcePath(String path) {
	super();
	this.path = path;
    }

}
