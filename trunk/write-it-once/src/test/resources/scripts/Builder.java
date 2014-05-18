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
