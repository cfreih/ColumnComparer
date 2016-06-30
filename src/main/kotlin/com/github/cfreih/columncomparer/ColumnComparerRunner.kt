package com.github.cfreih.columncomparer

import com.github.cfreih.columncomparer.dataclasses.ColumnInfo
import com.github.cfreih.columncomparer.services.ColumnComparer
import com.github.cfreih.columncomparer.services.MissingValuesDisplay
import com.github.cfreih.columncomparer.verification.ArgsVerifier


fun main(args: Array<String>) {
	ArgsVerifier(args).verify()
	val columnComparer = ColumnComparer.create(args)
	val missingValues : Map<ColumnInfo, List<String>> = columnComparer.findMissingValues()
	val display = MissingValuesDisplay(missingValues, columnComparer.masterColumn.fileName)
	println(display.buildMissingValuesDisplay())
}
