package com.github.cfreih.columncomparer.services

import com.github.cfreih.columncomparer.dataclasses.ColumnInfo

class MissingValuesDisplay(val missingValues : Map<ColumnInfo, List<String>>, val masterFileName : String ) {
    companion object Constants {
        private final val NEW_LINE : String = System.lineSeparator()
    }

    fun buildMissingValuesDisplay() : String {
        val displayBuilder = StringBuilder()
        displayBuilder.append(buildHeader())
        missingValues.entries.forEach {
            displayBuilder.append(buildMissingValuesEntry(it))
        }
        return displayBuilder.toString()
    }

    private fun buildHeader(): String {
        val headerBuilder = StringBuilder()
        headerBuilder.append("Comparing files to $masterFileName...")
                        .append(NEW_LINE)
        return headerBuilder.toString()
    }

    private fun buildMissingValuesEntry(entry : Map.Entry<ColumnInfo, List<String>>): String {
        val entryBuilder = StringBuilder(System.lineSeparator())
        val numberOfMissingValues : Int = entry.value.size
        val fileName : String = entry.key.fileName
        entryBuilder.append("$fileName has $numberOfMissingValues values different than $masterFileName:")
        for(missingValue in entry.value) {
            entryBuilder.append(System.lineSeparator())
                            .append(missingValue)
        }
        entryBuilder.append(System.lineSeparator())
        return entryBuilder.toString()
    }
}