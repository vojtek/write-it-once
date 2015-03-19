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
    <version>0.5.1</version>
</dependency>
```
  * A basic use of Reflections would be
  1. simple one file generation
```
// create generator
final Generator generator = GeneratorBuilder.instance().build();

// load template
final String template = FileUtils.read("src/test/resources/scripts/Builder.java");

// source code generation
final String sourceCode = generator.generate(Atest.class, template);

System.out.println(sourceCode);
```
  1. or multi file, universal generator
```
// create generator
final Generator generator = GeneratorBuilder.instance().build();

// load template
final String template = FileUtils.read("src/test/resources/scripts/Builder.java");

// Google reflections (optional) you can choose classes as you like
Reflections reflections = new Reflections("com.wk.simart.writeonce.domain");
final Set<Class<?>> datas = reflections.getTypesAnnotatedWith(Builder.class);

for (Class<?> data : datas) {
   // source code generation
   final String sourceCode = generator.generate(data, template);
   // target file name generation
   final String fileName = generator.generate(data, "${cls.shortName}Builder.java");
   // target file path
   final String filePath = generatedFilePatch + FileUtils.package2Path(data) + File.separator + fileName;
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
    private ${field.type} ${field.name};
<% } %>
<% for(field in cls.fields) {%>
    public ${cls.name}Builder ${field.name}(${field.type} ${field.name}) {
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
# For simple usage use maven plugin
```
<plugin>
	<groupId>org.simart</groupId>
	<artifactId>write-it-once-plugin</artifactId>
	<version>0.4.1</version>
	<configuration>
		<packageName>my.package</packageName>
		<units>
			<unit>
				<!-- script file -->
				<scriptFile>src/main/resources/scripts/Builder.java</scriptFile>
				<!-- search class with annotation -->
				<annotationName>my.package.some.crazy.stuff.CustomAnnotation</annotationName>
				<!-- generated file name (my/package/something/FooBuilder.java) -->
				<generatedFileNamePattern>${utils.package2Path(cls.package)}${cls.shortName}Builder.java</generatedFileNamePattern>
				<!-- generated files directory -->
				<generatedFileDirectory>src/main/generated/</generatedFileDirectory>
			</unit>
		</units>
	</configuration>
	<executions>
		<execution>
			<goals>
				<goal>generate</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```
## Documentation ##
  * access root element by **cls** of BeanClassDescriptor type, See com.wk.simart.writeonce.common for other type specification.
  * usage examples at https://code.google.com/p/write-it-once/source/browse/write-it-once-core/src/test/resources/scripts/TextClassDescriptor.txt
  * I know, I know, I know, I don't have doc... Because of lack of time... BUT! If you feel quite confused print just raw elements like ${cls} or ${cls.table} to print what is inside

### - ###
  * For easy map access see [Groovy Maps](http://groovy.codehaus.org/JN1035-Maps)
TODO
## Custom extensions ##
You can override every descriptor to add custom operations. Add your own DescriptorFactory or use existing one: _TypeFactory_, _FieldFactory_, MethodFactory, _PackageFactory_...
```
final Generator generator = GeneratorBuilder.instance()
                .override(new DescriptorFactory() {
                    
                    private Context context;
                    private TypeFactory typeFactory = new TypeFactory();

                    public void init(Context context) {
                        this.context = context;
                    }

                    public <T> T create(Class<T> cls, Object data) {
                        T t = typeFactory.create(cls, data);
                        return t;
                    }
                })
                .build();
```

---
