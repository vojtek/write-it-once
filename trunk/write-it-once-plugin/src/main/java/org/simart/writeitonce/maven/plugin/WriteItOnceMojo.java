package org.simart.writeitonce.maven.plugin;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.reflections.Reflections;
import org.simart.writeonce.common.Generator;
import org.simart.writeonce.common.GeneratorException;
import org.simart.writeonce.domain.GeneratorBuilder;
import org.simart.writeonce.utils.FileUtils;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.PROCESS_SOURCES, requiresDependencyResolution = ResolutionScope.RUNTIME)
public class WriteItOnceMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}")
    private MavenProject project;
    @Parameter(required = true)
    private String packageName;
    @Parameter(required = true)
    private List<Unit> units;

    // mvn org.simart:write-it-once-plugin:0.2-SNAPSHOT:write-it-once
    public void execute() throws MojoExecutionException, MojoFailureException {
        final URLClassLoader projectClassLoader = getProjectClassLoader();

        getLog().info(String.format("scaning %s", packageName));
        final Reflections reflections = new Reflections(packageName, projectClassLoader);

        final Generator generator = GeneratorBuilder.instance().build();

        for (Unit unit : units) {
            try {
                final String template = FileUtils.read(unit.getScriptFile());
                @SuppressWarnings("unchecked")
                final Set<Class<?>> datas = reflections.getTypesAnnotatedWith((Class<? extends Annotation>) projectClassLoader.loadClass(unit.getAnnotationName()));

                for (Class<?> data : datas) {
                    try {
                        final String sourceCode = generator.generate(data, template);
                        final String fileName = generator.generate(data, unit.getGeneratedFileNamePattern());
                        final String filePath = unit.getGeneratedFileDirectory() + fileName;

                        FileUtils.write(filePath, sourceCode);
                    } catch (IOException e) {
                        this.getLog().error(String.format("class: %s", data.getName()), e);
                        throw e;
                    }
                }
            } catch (ClassNotFoundException e) {
                this.getLog().error(String.format("annotation: %s, script: %s", unit.getAnnotationName(), unit.getScriptFile()), e);
            } catch (IOException e) {
                this.getLog().error(String.format("annotation: %s, script: %s", unit.getAnnotationName(), unit.getScriptFile()), e);
            } catch (GeneratorException e) {
                this.getLog().error(String.format("annotation: %s, script: %s", unit.getAnnotationName(), unit.getScriptFile()), e);
            }
        }
    }

    private URLClassLoader getProjectClassLoader() throws MojoExecutionException {
        try {
            final List<?> runtimeClasspathElements = project.getRuntimeClasspathElements();
            final URL[] runtimeUrls = new URL[runtimeClasspathElements.size()];
            for (int i = 0; i < runtimeClasspathElements.size(); i++) {
                final String element = (String) runtimeClasspathElements.get(i);
                runtimeUrls[i] = new File(element).toURI().toURL();
            }
            return new URLClassLoader(runtimeUrls, Thread.currentThread().getContextClassLoader());
        } catch (MalformedURLException e) {
            throw new MojoExecutionException("getting project class loader", e);
        } catch (DependencyResolutionRequiredException e) {
            throw new MojoExecutionException("getting project class loader", e);
        }
    }
}
