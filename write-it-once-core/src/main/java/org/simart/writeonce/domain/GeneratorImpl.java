package org.simart.writeonce.domain;

import groovy.text.GStringTemplateEngine;
import groovy.text.Template;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.control.CompilationFailedException;
import org.simart.writeonce.common.ClassDescriptor;
import org.simart.writeonce.common.Generator;
import org.simart.writeonce.common.GeneratorException;
import org.simart.writeonce.common.Utils;

public class GeneratorImpl implements Generator {

    private final Context context;

    GeneratorImpl(Context context) {
	super();
	this.context = context;
    }

    @Override
    public String generate(Class<?> cls, String template) throws GeneratorException {
	return generate(cls, template, null);
    }

    @Override
    public String generate(Class<?> cls, String template, Map<String, Object> customParameters) throws GeneratorException {
	try {
	    final GStringTemplateEngine templateEngine = new GStringTemplateEngine();
	    final Template compiledTemplate = templateEngine.createTemplate(template);
	    final Map<String, Object> binding = new HashMap<String, Object>();
	    binding.put("_context", context);
	    binding.put("utils", context.create(Utils.class, null));
	    binding.put("cls", context.create(ClassDescriptor.class, cls));
	    if (customParameters != null) {
		binding.putAll(customParameters);
	    }
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
