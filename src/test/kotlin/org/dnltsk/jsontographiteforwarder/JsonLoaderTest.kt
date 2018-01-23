package org.dnltsk.jsontographiteforwarder

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.junit4.SpringRunner
import java.io.IOException

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class JsonLoaderTest {

    @Autowired
    private lateinit var jsonLoader: JsonLoader

    @Value("\${local.server.port}")
    private lateinit var port: Integer

    private val fiveSeconds = 5_000

    @Test
    fun `load json works fine`() {
        val metricsUrl = "http://localhost:${port}/metrics"
        val jsonContent = jsonLoader.loadJson(metricsUrl, fiveSeconds)
        assertThat(jsonContent).isNotEmpty()
        assertThat(jsonContent).startsWith("{")
        assertThat(jsonContent).endsWith("}")
    }

    @Test
    @Ignore //FIXME ignored because the test is not working as expected
    fun `connection timeout throws IOException`() {
        val metricsUrl = "http://localhost:${port}/metrics"
        val oneMilli = 1
        assertThatThrownBy({
            val jsonContent = jsonLoader.loadJson(metricsUrl, oneMilli)
            println(jsonContent)
        }).isInstanceOf(IOException::class.java)
    }

    @Test
    @Ignore //FIXME ignored because the test is not working as expected
    fun `unavailable http url throws IOException`() {
        val unavailableHttpUrl = "http://foo-bar-bad-url.com/foofoo-barbar"
        assertThatThrownBy({
            jsonLoader.loadJson(unavailableHttpUrl, fiveSeconds)
        }).isInstanceOf(IOException::class.java)
    }

    @Test
    fun `unavailable https url throws IOException`() {
        val unavailableHttpsUrl = "https://localhost:${port}/metrics"
        assertThatThrownBy({
            jsonLoader.loadJson(unavailableHttpsUrl, fiveSeconds)
        }).isInstanceOf(IOException::class.java)
    }

}