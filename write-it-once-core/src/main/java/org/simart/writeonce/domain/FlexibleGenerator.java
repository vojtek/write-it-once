package org.simart.writeonce.domain;

import groovy.text.GStringTemplateEngine;
import groovy.text.Template;

import java.io.IOException;
import java.util.Map;

import org.codehaus.groovy.control.CompilationFailedException;
import org.simart.writeonce.common.DescriptorBuilder;
import org.simart.writeonce.common.GeneratorException;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

public class FlexibleGenerator {

    public static FlexibleGenerator create(String template) throws GeneratorException {
        return new FlexibleGenerator().template(template);
    }

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

    public <E> FlexibleGenerator bind(String fieldName, DescriptorBuilder<E> descriptorBuilder, E data) {
        binding.put(fieldName, descriptorBuilder.build(data));
        return this;
    }

    public <E> FlexibleGenerator cleanBindings() {
        binding.clear();
        return this;
    }

    public String generate() {
        return compiledTemplate.make(binding).toString();
    }

    private FlexibleGenerator() {
    }
}
