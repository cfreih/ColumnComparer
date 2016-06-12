package com.github.cfreih.columncomparer.services

import com.github.cfreih.columncomparer.dataclasses.ColumnInfo

class ColumnComparer(val masterColumn : ColumnInfo, val comparingColumns : List<ColumnInfo>) {

    fun findMissingValues() : Map<ColumnInfo, List<String>> {
        val missingValues : MutableMap<ColumnInfo, List<String>> = mutableMapOf()
        for(columnInfo in comparingColumns) {
            val missingColumnValuesFromColumnInfo : List<String> =
                    columnInfo.columnValues.filter { !masterColumn.columnValues.contains(it) }
            missingValues.put(columnInfo, missingColumnValuesFromColumnInfo)
        }
        return missingValues
    }

}