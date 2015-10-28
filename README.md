WRITE-IT-ONCE
=========================
*Code / script generator based on Java classes and template engine. Prepare your template (Groovy Template), choose Java classes (e.g. with Google Reflections) and forget*

**Generate almost everything based on java code and java comments**
- toString or getters / setters - generate Java or AspectJ files
- custom SQL script based on Java Annotations or simple database table create/modify commands - generate SQL script
- custom documentation, based on custom annotations, generate XML, DOC, HTML...

**Simplified language**
// TODO: doc

**Easy to extend**
// TODO: doc

## Table of Contents
- [Getting Started](#getting-started)
- [Expression examples](#expression-examples)
- [How to use / installation](#how-to-use-/-installation)
- [How to extend](#how-to-extend)
- [Plugins](#plugins)
	- [JPA plugin](#jpa-plugin)
		- [maven dependency](#maven-dependency)
		- [configure](#configure)
		- [expression examples](#expression-examples)
	- [Source comments plugin](#source-comments-plugin)
		- [maven dependency](#maven-dependency)
		- [configure](#configure)
		- [expression examples](#expression-examples)

## How to use / installation
```xml
<dependency>
    <groupId>org.simart</groupId>
    <artifactId>write-it-once-core</artifactId>
    <version>0.6.5</version>
</dependency>
```

## Getting Started
A basic use of Reflections would be
```java
// create generator with string groovy template
final Generator generator = Generator.create("Class ${cls.name} has ${cls.field['id'].name} field");

// bind builder (interpreter)
generator.bindBuilder("cls", Class.class); // cls - our variable, Class.class - type of interpreter
// bind value
generator.bindValue("cls", Atest.class);
// and generate
final String result = generator.generate();

// generate
assertThat(result ).isEqualTo("Class org.simart.writeonce.domain.Atest has id field");
```
or multi file, universal generator
```java
// find interesting classes - it's easy with org.reflections.Reflections
final Reflections reflections = new Reflections("org.simart.writeonce.domain");
        final Set<Class<?>> datas = reflections.getTypesAnnotatedWith(Builder.class);

// load template - Builder.java - this is not a java class - it's groovy template
final String template = FileUtils.read("src\\test\\resources\\scripts\\Builder.java");

// create generator
final Generator generator = Generator.create(template);

// define builder (interpreter)
generator.bindBuilder("cls", Class.class);

// go go go
for (Class<?> data : datas) {
   // bind value and generate
   final String sourceCode = generator.bind("cls", data).generate();
   // you can generate file name, too
   final String fileName = generator.generate("${cls.package.path}${cls.shortName}Builder.java");
   // save generated file
   final String filePath = generatedFilePatch + fileName;
   FileUtils.write(filePath, sourceCode);
}
```
The builder is custom runtime annotation, and script "src/test/resources/scripts/Builder.java"
```java
package ${cls.package.name};

public class ${cls.shortName}Builder {

    public static ${cls.name}Builder builder() {
        return new ${cls.name}Builder();
    }
<% for(field in cls.fields) {%>
    private ${field.type.name} ${field.name};
<% } %>
<% for(field in cls.fields) {%>
    public ${cls.name}Builder ${field.name}(${field.type.name} ${field.name}) {
        this.${field.name} = ${field.name};
        return this;
    }
<% } %>
    public ${cls.name} build() {
        final ${cls.name} data = new ${cls.name}();
<% for(field in cls.fields) {%>
        data.${field.setter.name}(this.${field.name});
<% } %>
        return data;
    }
}
```
result
```java
package org.simart.writeonce.domain;

public class AtestBuilder {

    public static org.simart.writeonce.domain.AtestBuilder builder() {
        return new org.simart.writeonce.domain.AtestBuilder();
    }

    private java.lang.Long id;

    private org.simart.writeonce.domain.Btest btest;

    private java.lang.String atestField;


    public org.simart.writeonce.domain.AtestBuilder id(java.lang.Long id) {
        this.id = id;
        return this;
    }

    public org.simart.writeonce.domain.AtestBuilder btest(org.simart.writeonce.domain.Btest btest) {
        this.btest = btest;
        return this;
    }

    public org.simart.writeonce.domain.AtestBuilder atestField(java.lang.String atestField) {
        this.atestField = atestField;
        return this;
    }

    public org.simart.writeonce.domain.Atest build() {
        final org.simart.writeonce.domain.Atest data = new org.simart.writeonce.domain.Atest();

        data.setId(this.id);

        data.setBtest(this.btest);

        data.setAtestField(this.atestField);

        return data;
    }
}
```
## Expression examples
standard reflection
```jsp
${cls.name} // full class name
${cls.field['id']}.type.name // id field type's full class name 
${cls.annotation['org.simart.writeonce.domain.Describe'].attribute.value()} // get Describe annotation value
```
## How to extend
```java
// given
final Generator generator = Generator.create("XXX${cls.name} ${cls.someValue} ${cls.isEnum} ${cls.interfaces[0].name}");
ReflectionPlugin.configure(generator);

generator.getContext().getBuilder(Class.class)
   .action("isEnum", new Action<Class>() {
      @Override
      public Object execute(Class data, Context context) {
         return data.isEnum();
      }
   })
   .action("interfaces", new Action<Class>() {
      @Override
      public Object execute(Class data, Context context) {
         return DescriptorBuilders.build(context.getBuilder(Class.class), Arrays.asList(data.getInterfaces()), context);
      }
   })
   .value("someValue", "YYYY");

// when
generator.bindBuilder("cls", Class.class);

// then
assertThat(generator.bindValue("cls", Atest.class).generate()).isEqualTo("XXXorg.simart.writeonce.domain.Atest YYYY false java.io.Serializable");
```
## Plugins
### JPA plugin
#### maven dependency
```xml
<dependency>
    <groupId>org.simart</groupId>
    <artifactId>write-it-once-jpa</artifactId>
    <version>0.6.5</version>
</dependency>
```
#### configure
```java
final Generator generator = Generator.create("${cls.table.name}");
JpaPlugin.configure(generator); // or JpaPlugin.configure(generator, <some column name resolver>, <column type resolver>, <table name resolver>);
final String result = generator.bind("cls", Class.class, Atest.class).generate();
assertThat(result).isEqualTo("A_TEST");
```
#### expression examples
```jsp
${cls.table.name}
${cls.field['atestField'].column.name} 
${cls.method['getBtest'].column.name}
${cls.method['getAtestField'].column.name} 
${cls.field['btest'].column.name}
${cls.column['ID'].annotation['javax.persistence.GeneratedValue'].attribute.strategy()}
```
### Source comments plugin
#### maven depdependency
```xml
<dependency>
    <groupId>org.simart</groupId>
    <artifactId>write-it-once-source</artifactId>
    <version>0.6.5</version>
</dependency>
```
#### configure
```java
final Generator generator = Generator.create("${cls.name} -> ${cls.comment}").lineSeparator("\n");
        SourcePlugin.configure(generator, SOURCE_PATH);
final String result = generator.bind("cls", Class.class, Atest.class).generate();
assertThat(result).isEqualTo("org.simart.writeonce.domain.Atest -> \n * Javadoc class comment\n * \n * @author Wojtek\n *\n ");
```
#### expression examples
```jsp
${cls.comment}
```
#### examples
// TODO:
