package acktsap.spring.application

import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.config.EncoderConfig
import io.restassured.config.LogConfig
import io.restassured.module.mockmvc.RestAssuredMockMvc
import io.restassured.module.mockmvc.config.RestAssuredMockMvcConfig
import io.restassured.module.mockmvc.kotlin.extensions.Given
import io.restassured.module.mockmvc.kotlin.extensions.Then
import io.restassured.module.mockmvc.kotlin.extensions.When
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.context.WebApplicationContext

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
internal class HomeControllerTest {

    @BeforeEach
    fun setUp(@Autowired webApplicationContext: WebApplicationContext, @Autowired objectMapper: ObjectMapper) {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext)
        RestAssuredMockMvc.config = RestAssuredMockMvcConfig.config()
            .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails())
            .encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))
    }

    @Test
    fun home() {
        Given {
            contentType(MediaType.APPLICATION_JSON_VALUE)
        } When {
            get("/")
        } Then {
            statusCode(HttpStatus.OK.value())
        }
    }
}
