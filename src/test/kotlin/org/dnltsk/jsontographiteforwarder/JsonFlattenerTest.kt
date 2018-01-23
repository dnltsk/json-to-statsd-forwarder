package org.dnltsk.jsontographiteforwarder

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class JsonFlattenerTest {

    @Autowired
    private lateinit var jsonFlattener: JsonFlattener

    @Test
    fun `flat json works fine`() {
        val flatJson = jsonFlattener.flattenJson("{\"foo\" : 1}")
        assertThat(flatJson).isEqualTo(mapOf(Pair("foo", 1)))
    }

    @Test
    fun `hierarchical json works fine`() {
        val flatJson = jsonFlattener.flattenJson("{\"foo\" : { \"foo2\" : 1 } }")
        assertThat(flatJson).isEqualTo(mapOf(Pair("foo.foo2", 1)))
    }

    @Test
    fun `dot in key name looks normal`() {
        val jmx = """
            {"mem":409288,
             "mem.free":295147
            }"""

        val flatJson = jsonFlattener.flattenJson(jmx)
        println(flatJson.keys)
        assertThat(flatJson.get("mem")).isEqualTo(409288)
        assertThat(flatJson.get("mem.free")).isEqualTo(295147)
    }

    @Test
    fun `hierarchical json with dot in key name works fine`() {
        val flatJson = jsonFlattener.flattenJson("{\"foo.dot\" : { \"foo2\" : 1 } }")
        assertThat(flatJson).isEqualTo(mapOf(Pair("foo.dot.foo2", 1)))
    }

    @Test
    fun `special characters are escaped by underscore`() {
        val flatJson = jsonFlattener.flattenJson("{\"foo x§x%x:x!x[x:x(x{x,x\" : 1 }")
        assertThat(flatJson).isEqualTo(mapOf(Pair("foo_x_x_x_x_x_x_x_x_x_x", 1)))
    }

    @Test
    fun `dots and dashes are not escaped by underscore`() {
        val flatJson = jsonFlattener.flattenJson("{\"foo-x.x\" : 1 }")
        assertThat(flatJson).isEqualTo(mapOf(Pair("foo-x.x", 1)))
    }
}