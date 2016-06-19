package com.github.cfreih.columncomparer.dataclasses

data class ArguementSet(val fileName : String, val delimiter : String, val columnNumber : Int) {
    companion object Factory {

        fun create(args: Array<String>) : List<ArguementSet> {
            val arguementSets : MutableList<ArguementSet> = mutableListOf()
            var index = 0
            while(index < args.size) {
                val fileName = args[index++]
                val delimiter = args[index++]
                val columnNumber = Integer.valueOf(args[index++])
                arguementSets.add(ArguementSet(fileName, delimiter, columnNumber))
            }
            return arguementSets
        }
    }
}


