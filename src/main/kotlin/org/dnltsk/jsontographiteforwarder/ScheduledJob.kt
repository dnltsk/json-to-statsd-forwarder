package org.dnltsk.jsontographiteforwarder

import com.timgroup.statsd.NonBlockingStatsDClient
import com.timgroup.statsd.StatsDClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.time.Instant


@Configuration
@EnableScheduling
class ScheduledJob {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Autowired
    lateinit var forwarderConfig: ForwarderConfig

    var statsd: StatsDClient? = null

    @Autowired
    lateinit var jsonLoader: JsonLoader

    @Autowired
    lateinit var jsonFlattener: JsonFlattener

    @Scheduled(fixedDelay = 5_000, initialDelay = 5_000)
    fun scheduleFixedRateTask() {
        log.info("#")
        log.info("# Fixed rate task - " + Instant.now())
        log.info("#")

        statsd = NonBlockingStatsDClient(
            forwarderConfig.statsdServer!!.prefix,
            forwarderConfig.statsdServer!!.host,
            forwarderConfig.statsdServer!!.port)

        forwarderConfig.statsdServer!!
        forwarderConfig.statsdServer!!.prefix

        for (forward in forwarderConfig.forwards!!) {
            try {
                handleForward(forward)
            } catch (e: Throwable) {
                log.error("error while handling ${forward.jsonSourceUrl}", e)
            }
        }
    }

    private fun handleForward(forward: Forward) {
        val jsonValue = jsonLoader.loadJson(forward.jsonSourceUrl!!, 5_000)
        val flatJson = jsonFlattener.flattenJson(jsonValue)
        flatJson.forEach { entry ->
            val doubleValue = entry.value.toString().toDoubleOrNull() ?: return
            val longValue = Math.round(doubleValue)
            val key = forward.targetPrefix!! + "." + entry.key
            log.info("$key: $doubleValue")
            statsd!!.recordGaugeValue(key, longValue);
        }
    }

}