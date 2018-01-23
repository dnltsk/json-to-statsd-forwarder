package org.dnltsk.jsontographiteforwarder

import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.net.URL

@Service
class JsonLoader {

    fun loadJson(jsonSourceUrl: String, connectionTimeoutInMillis: Int): String{
        val conn = URL(jsonSourceUrl).openConnection()
        conn.connectTimeout = connectionTimeoutInMillis
        val jsonContent = conn.getInputStream().bufferedReader().use(BufferedReader::readText)
        return jsonContent
    }

}