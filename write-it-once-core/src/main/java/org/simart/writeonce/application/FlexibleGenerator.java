package org.simart.writeonce.application;

import groovy.text.GStringTemplateEngine;
import groovy.text.Template;

import java.io.IOException;
import java.util.Map;

import org.codehaus.groovy.control.CompilationFailedException;
import org.simart.writeonce.common.GeneratorException;
import org.simart.writeonce.common.builder.DescriptorBuilder;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

public class FlexibleGenerator {

    public static FlexibleGenerator create(String template) throws GeneratorException {
        return new FlexibleGenerator().template(template);
    }

    private Map<String, DescriptorBuilder<? extends Object>> builders = Maps.newHashMap();
    private Map<String, Object> binding = Maps.newHashMap();
    private Template compiledTemplate;

    public FlexibleGenerator template(String template) throws GeneratorException {
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
    public <E> FlexibleGenerator bind(String fieldName, E data) {
        final DescriptorBuilder<E> descriptorBuilder = (DescriptorBuilder<E>) builders.get(fieldName);
        binding.put(fieldName, descriptorBuilder == null ? data : descriptorBuilder.build(data));
        return this;
    }

    public <E> FlexibleGenerator bind(String fieldName, DescriptorBuilder<E> descriptorBuilder, E data) {
        builders.put(fieldName, descriptorBuilder);
        binding.put(fieldName, descriptorBuilder.build(data));
        return this;
    }

    public <E> FlexibleGenerator descriptor(String fieldName, DescriptorBuilder<E> descriptorBuilder) {
        builders.put(fieldName, descriptorBuilder);
        return this;
    }

    public <E> FlexibleGenerator cleanBindings() {
        binding.clear();
        return this;
    }

    public String generate() {
        return compiledTemplate.make(binding).toString();
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

    private FlexibleGenerator() {
    }
}
