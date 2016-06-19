package com.github.cfreih.columncomparer.services

import com.github.cfreih.columncomparer.dataclasses.ArguementSet
import com.github.cfreih.columncomparer.dataclasses.ColumnInfo

class ColumnComparer(val masterColumn : ColumnInfo, val comparingColumns : List<ColumnInfo>) {

    companion object Factory {
        fun create(args : Array<String>) : ColumnComparer {
            val argsSets : List<ArguementSet> = ArguementSet.create(args)
            val masterColumn : ColumnInfo = ColumnInfo.create(argsSets[0])
            val comparingColumns : MutableList<ColumnInfo> = mutableListOf()
            for(i in 1..argsSets.size-1) {
                comparingColumns.add(ColumnInfo.create(argsSets[i]))
            }
            return ColumnComparer(masterColumn, comparingColumns)
        }
    }

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