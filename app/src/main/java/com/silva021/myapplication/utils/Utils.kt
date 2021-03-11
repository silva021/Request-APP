package com.silva021.myapplication.utils

import java.util.*

class Utils {
    companion object Method {
        val REQUEST_CODE_SAVED_REQUEST : Int  = 21
        fun returnDate(): String {
            val date = Calendar.getInstance()
            return date.get(Calendar.DAY_OF_MONTH)
                .toString() + "-" + (date.get(Calendar.MONTH) + 1).toString() + "-" + date.get(
                Calendar.YEAR
            )
                .toString()

        }

        fun formatJson(json: String): String {
            val jsonBuilder = StringBuilder()
            var indentString = ""
            (json.indices).forEach { i ->
                when (val letter = json[i]) {
                    '{', '[' -> {
                        jsonBuilder.append("\n" + indentString + letter + "\n")
                        indentString += "\t\t";
                        jsonBuilder.append(indentString);
                    }
                    '}', ']' -> {
                        indentString = indentString.replaceFirst("\t\t", "")
                        jsonBuilder.append("\n" + indentString + letter)
                    }
                    ',' -> jsonBuilder.append(letter + "\n" + indentString);
                    else -> jsonBuilder.append(letter);
                }
            }
            return jsonBuilder.toString()
        }
    }


}