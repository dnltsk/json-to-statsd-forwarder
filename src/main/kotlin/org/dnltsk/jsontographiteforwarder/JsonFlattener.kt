package org.dnltsk.jsontographiteforwarder

import com.github.wnameless.json.flattener.JsonFlattener
import org.springframework.stereotype.Service
import kotlin.math.roundToInt

@Service
class JsonFlattener {

    fun flattenJson(jsonContent: String): Map<String, Int> {
        val flatJson = JsonFlattener.flattenAsMap(jsonContent);
        val sanitizedFlatMap = flatJson
            .filterValues { it.toString().toDoubleOrNull() != null }
            .mapValues { it.value.toString().toDouble().roundToInt() }
            .mapKeys {
                var newKey = it.key
                newKey = removeWrapping(newKey)
                newKey = maskSpecialChars(newKey)
                newKey
            }
        return sanitizedFlatMap
    }

    private fun removeWrapping(key: String): String {
        var newKey = key.replace("[\\\"", "")
        newKey = newKey.replace("\\\"]", "")
        return newKey
    }

    private fun maskSpecialChars(key: String): String {
        val allNonWordsButDot = "[^\\w\\.-]"
        return key.replace(Regex(allNonWordsButDot), "_")
    }

}