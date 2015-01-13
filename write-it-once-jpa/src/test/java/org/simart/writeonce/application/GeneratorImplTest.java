package org.simart.writeonce.application;

import static org.fest.assertions.Assertions.assertThat;

import org.simart.writeonce.common.ColumnNameResolver;
import org.simart.writeonce.common.ColumnTypeResolver;
import org.simart.writeonce.common.GeneratorException;
import org.simart.writeonce.common.TableNameResolver;
import org.simart.writeonce.common.builder.JpaPlugin;
import org.simart.writeonce.domain.Atest;
import org.testng.annotations.Test;

public class GeneratorImplTest {

    @Test
    public void table() throws GeneratorException {
	// given
	final Generator generator = Generator.create("${cls.table.name}");
	JpaPlugin.configure(generator);

	// when
	final String result = generator.bind("cls", Class.class, Atest.class).generate();

	// then
	assertThat(result).isEqualTo("A_TEST");
    }

    @Test
    public void columnByField() throws GeneratorException {
	// given
	final Generator generator = Generator.create("${cls.field['atestField'].column.name} ${cls.method['getBtest'].column.name}");
	JpaPlugin.configure(generator, new UpperCaseColumnNameResolver(), new FakeColumnTypeResolver(), new UpperCaseTableNameResolver());

	// when
	final String result = generator.bind("cls", Class.class, Atest.class).generate();

	// then
	assertThat(result).isEqualTo("AT_EST B_TEST");
    }

    @Test
    public void columnByMethod() throws GeneratorException {
	// given
	final Generator generator = Generator.create("${cls.method['getAtestField'].column.name} ${cls.field['btest'].column.name}");
	JpaPlugin.configure(generator, new UpperCaseColumnNameResolver(), new FakeColumnTypeResolver(), new UpperCaseTableNameResolver());

	// when
	final String result = generator.bind("cls", Class.class, Atest.class).generate();

	// then
	assertThat(result).isEqualTo("AT_EST B_TEST");
    }

    @Test
    public void columnAnnotations() throws GeneratorException {
	// given
	final Generator generator = Generator.create("${cls.column['ID'].annotation['javax.persistence.GeneratedValue'].attribute.strategy()}");
	JpaPlugin.configure(generator, new UpperCaseColumnNameResolver(), new FakeColumnTypeResolver(), new UpperCaseTableNameResolver());

	// when
	final String result = generator.bind("cls", Class.class, Atest.class).generate();

	// then
	assertThat(result).isEqualTo("AUTO");

    }

    private class UpperCaseColumnNameResolver implements ColumnNameResolver {
	@Override
	public String getName(String fieldName) {
	    return fieldName.toUpperCase();
	}
    }

    private class FakeColumnTypeResolver implements ColumnTypeResolver {
	@Override
	public String getType(ColumnTypeResolver.TypeDescriptor typeDescriptor) {
	    return "FAKE_TYPE";
	}

	@Override
	public String getFullType(ColumnTypeResolver.TypeDescriptor typeDescriptor) {
	    return "FAKE_FULL_TYPE";
	}
    }

    private class UpperCaseTableNameResolver implements TableNameResolver {
	@Override
	public String getName(Class<?> cls) {
	    return cls.getSimpleName().toUpperCase();
	}
    }
}
