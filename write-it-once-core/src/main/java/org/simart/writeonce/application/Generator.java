package org.simart.writeonce.application;

import groovy.lang.Writable;
import groovy.text.GStringTemplateEngine;
import groovy.text.Template;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.codehaus.groovy.control.CompilationFailedException;
import org.simart.writeonce.common.GeneratorException;
import org.simart.writeonce.common.SourcePath;
import org.simart.writeonce.common.builder.DescriptorBuilder;
import org.simart.writeonce.common.builder.ReflectionPlugin;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

public class Generator {

    public static Generator create(String template) throws GeneratorException {
	return new Generator().template(template);
    }

    public static Generator create() throws GeneratorException {
	return new Generator();
    }

    private final Context context;
    private Map<String, Object> binding = Maps.newHashMap();
    private Template compiledTemplate;
    private String lineSeparator;

    public Generator template(String template) throws GeneratorException {
	Preconditions.checkNotNull(template);
	final GStringTemplateEngine templateEngine = new GStringTemplateEngine();
	try {
	    compiledTemplate = templateEngine.createTemplate(template);
	    return this;
	} catch (CompilationFailedException e) {
	    throw new GeneratorException(e);
	} catch (ClassNotFoundException e) {
	    throw new GeneratorException(e);
	} catch (IOException e) {
	    throw new GeneratorException(e);
	}
    }

    @SuppressWarnings("unchecked")
    public <E> Generator bind(String fieldName, Class<?> builderType, E data) {
	final DescriptorBuilder<E> descriptorBuilder = (DescriptorBuilder<E>) context.getBuilder(builderType);
	binding.put(fieldName, descriptorBuilder == null ? data : descriptorBuilder.build(data, context));
	return this;
    }

    public <E> Generator bindValue(String fieldName, E data) {
	bind(fieldName, data.getClass(), data);
	return this;
    }

    public <E> Generator bindBuilder(String fieldName, Class<?> builderType) {
	binding.put(fieldName, context.getBuilder(builderType));
	return this;
    }

    public Generator cleanBuilderBindings() {
	binding.clear();
	return this;
    }

    public Generator lineSeparator(String lineSeparator) {
	this.lineSeparator = lineSeparator;
	return this;
    }

    public <T> Generator register(Class<?> type, DescriptorBuilder<?> descriptorBuilder) {
	context.register(type, descriptorBuilder);
	return this;
    }

    public <T> DescriptorBuilder<T> getBuilder(Class<T> type) {
	return context.getBuilder(type);
    }

    public <E> Generator setHelper(Class<? extends E> type, E helper) {
	context.setHelper(type, helper);
	return this;
    }

    public <E> Generator setHelper(E helper) {
	context.setHelper(helper.getClass(), helper);
	return this;
    }

    public String generate() {
	final Writable writable = compiledTemplate.make(binding);
	final StringWriter stringWriter = new StringWriter();
	final FixedLineWriter platformLineWriter = new FixedLineWriter(stringWriter, lineSeparator);
	try {
	    writable.writeTo(platformLineWriter);
	    platformLineWriter.flush();
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
	return stringWriter.toString();
    }

    Context getContext() {
	return context;
    }

    public String generate(String template) throws GeneratorException {
	Preconditions.checkNotNull(template);
	final GStringTemplateEngine templateEngine = new GStringTemplateEngine();
	try {
	    final Template compiledTemplate = templateEngine.createTemplate(template);
	    return compiledTemplate.make(binding).toString();
	} catch (CompilationFailedException e) {
	    throw new GeneratorException(e);
	} catch (ClassNotFoundException e) {
	    throw new GeneratorException(e);
	} catch (IOException e) {
	    throw new GeneratorException(e);
	}
    }

    Generator() {
	this.context = createContext();
	this.setHelper(SourcePath.class, SourcePath.create("src" + File.separator + "test" + File.separator + "java" + File.separator));
	ReflectionPlugin.configure(this);
    }

    Context createContext() {
	return new Context();
    }
}
