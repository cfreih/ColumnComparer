package com.github.cfreih.columncomparer.services

import java.io.File

fun populateFile(fileName : String, vararg lines : String) {
    File(fileName).printWriter().use { out ->
        lines.forEach { out.println(it) }
    }
}
