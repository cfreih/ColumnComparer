package com.github.cfreih.columncomparer.input

import org.apache.commons.lang3.StringUtils

fun parseArgs(arguments : Array<String>) {
	var errorMessage : StringBuilder = verifyArguments(arguments);
	
	if(!StringUtils.isBlank(errorMessage)) {
		throw IllegalArgumentException(errorMessage.toString())
	}
}

fun verifyArguments(arguments : Array<String>) : StringBuilder {
	var message = StringBuilder()
	message.append(verifyNumberOfArguments(arguments.size));
	
	return message
}
fun verifyNumberOfArguments(numberOfArguments: Int) : StringBuilder {
	var sb = StringBuilder()
	if(numberOfArguments < 6) {
		sb.append("There needs to be at least 6 arguments.")
	}
	if(numberOfArguments % 3 != 0) {
		sb.append("The arguments should have a multiple of 3 in format \"file delimiter columnNumber\"}")
	}
	return sb
}
