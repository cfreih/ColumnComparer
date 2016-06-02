package com.github.cfreih.columncomparer.input

import org.apache.commons.lang3.StringUtils
import java.io.File

class ArgsVerifier(val arguments: Array<String>) {

	internal val errorMessages: MutableList<String> = mutableListOf()
		get() = field
    private val argumentSetSize = 3
    private val minArguments : Int
        get() = argumentSetSize * 2
    private val fileSetIndex = 0
    private val columnSetIndex = 2

	fun verify() {
		verifyNumberOfArguments()
		verifyArgumentsValidTypes()

		if(errorMessages.size > 0) {
			throw IllegalArgumentException(StringUtils.join(errorMessages.toTypedArray(), "\n\t\t"))
		}
	}

	internal fun verifyNumberOfArguments() {
		if(arguments.size < minArguments) {
			errorMessages.add("You have ${arguments.size} arguments, but need at least six")
		}
		if(arguments.size % argumentSetSize != 0) {
			errorMessages.add("Arguments should be grouped in sets of three like \"fileName delimiter columnNumber\"")
		}
	}

	internal fun verifyArgumentsValidTypes() {
		var index = 0
		while(index < arguments.size) {
			when {
				index % argumentSetSize == fileSetIndex -> verifyArgumentIsFile(index)
				index % argumentSetSize == columnSetIndex -> verifyArgumentIsInteger(index)
			}
			index++
		}
	}

	internal fun verifyArgumentIsFile(argumentIndex : Int) {
		val file = File(arguments[argumentIndex])
		if(!file.isFile) {
			addArgumentAtIndexErrorMessage(argumentIndex, "is not a valid file or does not exist")
		}
	}

	internal fun verifyArgumentIsInteger(argumentIndex : Int) {
		if(!StringUtils.isNumeric(arguments[argumentIndex])) {
			addArgumentAtIndexErrorMessage(argumentIndex, "is not an integer")
		}
	}

	private fun addArgumentAtIndexErrorMessage(argumentIndex : Int, message : String) {
		errorMessages.add("\"${arguments[argumentIndex]}\" at index $argumentIndex $message")
	}
}