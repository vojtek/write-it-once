package org.simart.writeonce.common.builder;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Map;

import org.simart.writeonce.domain.Atest;
import org.simart.writeonce.domain.Builder;
import org.testng.annotations.Test;

public class AnnotationDescriptorBuilderTest {

    @Test
    public void type() {
        @SuppressWarnings("unchecked")
        final Map<String, Object> descriptors = (Map<String, Object>) AnnotationDescriptorBuilder.create().build(Atest.class.getAnnotation(Builder.class)).get("type");
        assertThat(descriptors.get("name")).isEqualTo(Builder.class.getName());
    }

    @Test
    public void attribute() {
        final Builder builder = (Builder) AnnotationDescriptorBuilder.create().build(Atest.class.getAnnotation(Builder.class)).get("attribute");
        assertThat(builder.alias()).isEqualTo("xxx");
    }

}
