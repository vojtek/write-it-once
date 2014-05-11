package com.wk.simart.writeonce.domain;

import groovy.text.GStringTemplateEngine;
import groovy.text.Template;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.control.CompilationFailedException;

import com.wk.simart.writeonce.common.ClassDescriptor;
import com.wk.simart.writeonce.common.Generator;
import com.wk.simart.writeonce.common.GeneratorException;

public class GeneratorImpl implements Generator {

    private final Context context;

    GeneratorImpl(Context context) {
        super();
        this.context = context;
    }

    public String generate(Class<?> cls, String template) throws GeneratorException {
        try {
            final GStringTemplateEngine templateEngine = new GStringTemplateEngine();
            final Template compiledTemplate = templateEngine.createTemplate(template);
            final Map<String, Object> binding = new HashMap<String, Object>();
            binding.put("context", context);
            binding.put("cls", context.create(ClassDescriptor.class, cls));
            return compiledTemplate.make(binding).toString();
        } catch (CompilationFailedException e) {
            throw new GeneratorException(e);
        } catch (ClassNotFoundException e) {
            throw new GeneratorException(e);
        } catch (IOException e) {
            throw new GeneratorException(e);
        }
    }
}
