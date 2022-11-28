package acktsap.spring.resttemplate.converter

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity

fun main(args: Array<String>) {
    val server = MockWebServer().apply {
        start()
    }
    val baseUrl = server.url("/test")

    val restTemplate = RestTemplate(getHttpMessageConverters())
    server.enqueue(MockResponse().addHeader("Content-Type", "application/x-www-form-urlencoded").setBody("""
    {
        "intValue": 3,
        "stringValue": "wow",
        "extraValue": "extra"
    }
    """.trimIndent()))

    val requestBody = mapOf(
        "test" to "value"
    )

    val response = restTemplate.postForEntity<Map<String, String>>(baseUrl.toUri(), requestBody)
    println("body: " + response.body)
    restTemplate.close()

    server.shutdown()
}

private fun getHttpMessageConverters(): List<HttpMessageConverter<*>>? {
    val mappingJackson2HttpMessageConverter = MappingJackson2HttpMessageConverter()
    mappingJackson2HttpMessageConverter.supportedMediaTypes = listOf(
        MediaType.TEXT_PLAIN,
        MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_FORM_URLENCODED,
    )
    val stringHttpMessageConverter = StringHttpMessageConverter()
    return java.util.List.of<HttpMessageConverter<*>>(
        mappingJackson2HttpMessageConverter,
        stringHttpMessageConverter
    )
}
