package org.simart.writeitonce.maven.plugin;


public class Unit {
    private String scriptFile;
    private String annotationName;
    private String generatedFileNamePattern;
    private String generatedFileDirectory;

    public String getScriptFile() {
        return scriptFile;
    }

    public void setScriptFile(String scriptFile) {
        this.scriptFile = scriptFile;
    }

    public String getAnnotationName() {
        return annotationName;
    }

    public void setAnnotationName(String annotationName) {
        this.annotationName = annotationName;
    }

    public String getGeneratedFileNamePattern() {
        return generatedFileNamePattern;
    }

    public void setGeneratedFileNamePattern(String generatedFileNamePattern) {
        this.generatedFileNamePattern = generatedFileNamePattern;
    }

    public String getGeneratedFileDirectory() {
        return generatedFileDirectory;
    }

    public void setGeneratedFileDirectory(String generatedFileDirectory) {
        this.generatedFileDirectory = generatedFileDirectory;
    }

    @Override
    public String toString() {
        return "Unit [scriptFile=" + scriptFile + ", annotationName=" + annotationName + ", generatedFileNamePattern=" + generatedFileNamePattern + ", generatedFileDirectory="
                + generatedFileDirectory + "]";
    }

}
