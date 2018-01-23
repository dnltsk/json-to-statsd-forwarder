package org.dnltsk.jsontographiteforwarder

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

@SpringBootApplication
@EnableConfigurationProperties(ForwarderConfig::class)
class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
