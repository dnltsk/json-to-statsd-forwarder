package org.dnltsk.jsontographiteforwarder

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "forwarder")
class ForwarderConfig(
    var statsdServer: StatsdServer? = null,
    var refreshIntervalInSeconds: Int = 5,
    var forwards: List<Forward>? = null
)

data class Forward(
    var jsonSourceUrl: String? = null,
    var targetPrefix: String? = null
)

open class StatsdServer(
    var host: String = "localhost",
    var port: Int = 8125,
    var prefix: String = "forwarder"
)