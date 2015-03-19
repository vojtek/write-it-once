# Code / script generator based on Java classes and template engine #
Prepare your template ([Groovy Template](http://groovy.codehaus.org/Groovy+Templates)), choose Java classes (e.g. with [Google Reflections](https://code.google.com/p/reflections)) and forget.

  * flexible code generator like _toString_ or _getters_ / _setters_ - generate _Java_ or _AspectJ_ files
  * custom _SQL script_ based on _Java Annotations_ or simple database table create/modify commands - generate _SQL script_
  * custom documentation based on your _Java_ project - generate _XML_, _DOC_, _HTML_...
## How to use? ##
  * Add write-it-once to your project.
  1. For maven projects just add this dependency:
```
<dependency>
    <groupId>org.simart</groupId>
    <artifactId>write-it-once-core</artifactId>
    <version>0.6.2</version>
</dependency>
```
  * A basic use of Reflections would be
  1. simple one file generation
```
// create generator with string groovy template
final FlexibleGenerator generator = FlexibleGenerator.create("XXX${cls.name}");

// load template
final String template = FileUtils.read("src/test/resources/scripts/Builder.java");

// bind class decorator, and data
final String result = generator.bind("cls", ClassDescriptorBuilder.create(), Atest.class)

// generate
assertThat(generator.generate()).isEqualTo("XXXorg.simart.writeonce.domain.Atest");
```
  1. or multi file, universal generator
```
// find interesting classes - it's easy with org.reflections.Reflections
final Reflections reflections = new Reflections("org.simart.writeonce.domain");
        final Set<Class<?>> datas = reflections.getTypesAnnotatedWith(Builder.class);

// load template - Builder.java - this is not a java class - it's groovy template
final String template = FileUtils.read("src\\test\\resources\\scripts\\Builder.java");

// create generator
final FlexibleGenerator generator = FlexibleGenerator.create(template);

// define decirators
generator.descriptor("cls", DescriptorBuilders.createClassDescriptorBuilder());

// go go go
for (Class<?> data : datas) {
   // bind data to decorator
   final String sourceCode = generator.bind("cls", data).generate();
   // you can generate file name, too
   final String fileName = generator.generate("${cls.package.path}${cls.shortName}Builder.java");
   // save generated file
   final String filePath = generatedFilePatch + fileName;
   FileUtils.write(filePath, sourceCode);
}
```
The builder is custom runtime annotation, and script _"src/test/resources/scripts/Builder.java"_:
```
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
## Extend ##
```
// some generator
final FlexibleGenerator generator = FlexibleGenerator.create("XXX${cls.name} ${cls.someValue} ${cls.isEnum} ${cls.interfaces[0].name}");

// some custom extensions
final DescriptorBuilder<Class<?>> descriptorBuilder = ClassDescriptorBuilder.create()
   // action - dynamic extension - execute on binding             
   .action("isEnum", new Action<Class<?>>() {
                    @Override
                    public Object execute(Class<?> data) {
                        return data.isEnum();
                    }
                })
   // use prepared builders to simplify code             
   .action("interfaces", new Action<Class<?>>() {
                    @Override
                    public Object execute(Class<?> data) {
                        return DescriptorBuilders.build(ClassDescriptorBuilder.create(), Arrays.asList(data.getInterfaces()));
                    }
                })
   // custom values / objects             
   .value("someValue", "YYYY");

// add descriptor to generator
generator.descriptor("cls", descriptorBuilder);

// see how easy it is
assertThat(generator.bind("cls", Atest.class).generate()).isEqualTo("XXXorg.simart.writeonce.domain.Atest YYYY false java.io.Serializable")
```
### Documentation ###
# See predefined builders - create method for more informations
  * https://code.google.com/p/write-it-once/source/browse/write-it-once-core/src/main/java/org/simart/writeonce/common/builder/DefaultDescriptorBuilder.java - basic builder
  * https://code.google.com/p/write-it-once/source/browse/write-it-once-core/src/main/java/org/simart/writeonce/common/builder/ClassDescriptorBuilder.java - for Class
  * https://code.google.com/p/write-it-once/source/browse/write-it-once-core/src/main/java/org/simart/writeonce/common/builder/FieldDescriptorBuilder.java - for Field
  * https://code.google.com/p/write-it-once/source/browse/write-it-once-core/src/main/java/org/simart/writeonce/common/builder/MethodDescriptorBuilder.java - for Method
  * https://code.google.com/p/write-it-once/source/browse/write-it-once-core/src/main/java/org/simart/writeonce/common/builder/MethodParameterDescriptorBuilder.java - for method parameters
  * https://code.google.com/p/write-it-once/source/browse/write-it-once-core/src/main/java/org/simart/writeonce/common/builder/PackageDescriptorBuilder.java - for package
### - ###
  * For easy map access see [Groovy Maps](http://groovy.codehaus.org/JN1035-Maps)