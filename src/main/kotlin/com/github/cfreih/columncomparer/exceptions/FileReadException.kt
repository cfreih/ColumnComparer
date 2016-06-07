package com.github.cfreih.columncomparer.exceptions

class FileReadException : Exception {
    constructor(message : String = "Error reading a file", ex : Exception?) : super(message, ex)
}