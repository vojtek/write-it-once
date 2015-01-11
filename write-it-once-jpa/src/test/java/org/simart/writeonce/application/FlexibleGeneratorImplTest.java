package org.simart.writeonce.application;

import static org.fest.assertions.Assertions.assertThat;

import java.lang.reflect.Field;

import javax.persistence.Column;

import org.simart.writeonce.common.ColumnNameResolver;
import org.simart.writeonce.common.ColumnTypeResolver;
import org.simart.writeonce.common.GeneratorException;
import org.simart.writeonce.common.TableNameResolver;
import org.simart.writeonce.common.builder.JpaPlugin;
import org.simart.writeonce.domain.Atest;
import org.testng.annotations.Test;

public class FlexibleGeneratorImplTest {

    @Test
    public void table() throws GeneratorException {
	// given
	final FlexibleGenerator generator = FlexibleGenerator.create("${cls.table.name}");
	JpaPlugin.configure(generator);

	// when
	final String result = generator.bind("cls", Class.class).evaluate("cls", Atest.class).generate();

	// then
	assertThat(result).isEqualTo("A_TEST");
    }

    @Test
    public void columnByField() throws GeneratorException {
	// given
	final FlexibleGenerator generator = FlexibleGenerator.create("${cls.field['atestField'].column.name} ${cls.method['getBtest'].column.name}");
	JpaPlugin.configure(generator, new UpperCaseColumnNameResolver(), new FakeColumnTypeResolver(), new UpperCaseTableNameResolver());

	// when
	final String result = generator.bind("cls", Class.class).evaluate("cls", Atest.class).generate();

	// then
	assertThat(result).isEqualTo("AT_EST B_TEST");
    }

    @Test
    public void columnByMethod() throws GeneratorException {
	// given
	final FlexibleGenerator generator = FlexibleGenerator.create("${cls.method['getAtestField'].column.name} ${cls.field['btest'].column.name}");
	JpaPlugin.configure(generator, new UpperCaseColumnNameResolver(), new FakeColumnTypeResolver(), new UpperCaseTableNameResolver());

	// when
	final String result = generator.bind("cls", Class.class).evaluate("cls", Atest.class).generate();

	// then
	assertThat(result).isEqualTo("AT_EST B_TEST");
    }

    private class UpperCaseColumnNameResolver implements ColumnNameResolver {
	@Override
	public String getName(String fieldName) {
	    return fieldName.toUpperCase();
	}
    }

    private class FakeColumnTypeResolver implements ColumnTypeResolver {
	@Override
	public String getType(Column column, Class<?> typeClass, Field field) {
	    return "FAKE_TYPE";
	}

	@Override
	public String getFullType(Column column, Class<?> typeClass, Field field) {
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
