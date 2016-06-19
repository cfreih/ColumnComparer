package com.github.cfreih.columncomparer.dataclasses

import com.github.cfreih.columncomparer.exceptions.FileReadException
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

data class ColumnInfo(val fileName : String, val columnValues : List<String>) {
    companion object Factory {
        fun create(argSet: ArguementSet) : ColumnInfo {
            val lines : MutableList<String> = retrieveLinesOfFile(File(argSet.fileName))
            val columnValues : List<String> = retrieveColumnValues(lines, argSet)
            return ColumnInfo(argSet.fileName, columnValues)
        }

        private fun retrieveLinesOfFile(file: File): MutableList<String> {
            val lines : MutableList<String> = mutableListOf()
            InputStreamReader(file.inputStream()).use {
                BufferedReader(it).use {
                    lines.addAll(it.readLines())
                }
            }
            return lines
        }

        private fun retrieveColumnValues(lines : MutableList<String>, args : ArguementSet) : List<String> {
            try {
                val columnValues = lines.map({
                    val columns = it.split(delimiters = args.delimiter)
                    columns[args.columnNumber]
                })
                return columnValues
            } catch(ex : IndexOutOfBoundsException) {
                throw FileReadException("Invalid column number ${args.columnNumber} or " +
                        "delimiter ${args.fileName} for ${args.fileName}", ex)
            }
        }
    }
}


