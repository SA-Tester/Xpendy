package com.example.xpendy

class Formats {
    fun formatStringToArray(input: String): Map<String, Float> {
        val regex = Regex("""(\w+)\s*:\s*(\d+)""")
        val matchResults = regex.findAll(input)
        val resultMap = mutableMapOf<String, Float>()
        for (matchResult in matchResults) {
            val (key, value) = matchResult.destructured
            resultMap[key] = value.toFloat()
        }
        return resultMap
    }
}