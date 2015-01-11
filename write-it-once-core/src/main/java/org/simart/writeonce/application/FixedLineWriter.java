package org.simart.writeonce.application;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class FixedLineWriter extends Writer {

    private String lineSeparator;
    private BufferedWriter writer;

    public FixedLineWriter(Writer out, String lineSeparator) {
	writer = new BufferedWriter(out);
	this.lineSeparator = lineSeparator;
    }

    public FixedLineWriter(Writer out, int sz, String lineSeparator) {
	writer = new BufferedWriter(out, sz);
	this.lineSeparator = lineSeparator;
    }

    public void write(char cbuf[], int off, int len) throws IOException {
	for (; len > 0; len--) {
	    char c = cbuf[off++];
	    if (c == '\n') {
		if (lineSeparator != null) {
		    writer.write(lineSeparator);
		} else {
		    writer.newLine();
		}
	    } else if (c != '\r') {
		writer.write(c);
	    }
	}
    }

    public void flush() throws IOException {
	writer.flush();
    }

    public void close() throws IOException {
	writer.close();
    }
}
