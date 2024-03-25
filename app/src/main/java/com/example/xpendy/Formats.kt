package com.example.xpendy

import android.widget.TableLayout
import java.util.Calendar

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

    fun cleanTable(table: TableLayout){
        val childCount: Int = table.childCount
        if (childCount > 1) {
            table.removeViews(1, childCount - 1)
        }
    }

    fun getCurrentMonth(): String {
        val calendar = Calendar.getInstance()
        val monthIndex = calendar.get(Calendar.MONTH)
        val monthsArray = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )
        return monthsArray[monthIndex]
    }
}