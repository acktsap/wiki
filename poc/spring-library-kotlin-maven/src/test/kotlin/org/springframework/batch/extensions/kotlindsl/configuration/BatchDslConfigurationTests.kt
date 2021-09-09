package org.springframework.batch.extensions.kotlindsl.configuration

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.batch.core.configuration.annotation.EnableBatchDsl
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import javax.sql.DataSource

internal class BatchDslConfigurationTests {

    @Test
    fun testBeanRegistration() {
        val context = AnnotationConfigApplicationContext(TestConfiguration::class.java)
        val batch = context.getBean(BatchDsl::class.java)

        assertThat(batch).isNotNull
    }

    @Configuration
    @EnableBatchProcessing
    @EnableBatchDsl
    private open class TestConfiguration {

        @Bean
        fun dataSource(): DataSource {
            return EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .generateUniqueName(true)
                .build()
        }
    }
}
