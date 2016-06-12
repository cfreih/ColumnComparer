package com.github.cfreih.columncomparer.dataclasses

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class CreateArguementSetsTest {

    lateinit var args : Array<String>

    @Test
    fun emptyArgsShouldReturnEmptyList() {
        //setup
        args = arrayOf()

        //execute
        val result : List<ArguementSet> = createArguementSets(args)

        //assert
        assertEquals(0, result.size)
    }

    @Test
    fun argsShouldReturnListContainingProperSets() {
        //setup
        args = arrayOf("test.txt", ",", "3", "test.csv", "/", "1003")

        //execute
        val result : List<ArguementSet> = createArguementSets(args)

        //assert
        assertEquals(2, result.size)
        assertTrue{result.contains(ArguementSet("test.txt", ",", 3))}
        assertTrue(result.contains(ArguementSet("test.csv", "/", 1003)))
    }

    @Test(expected = ArrayIndexOutOfBoundsException::class)
    fun badArgumentsSizeShouldThrowArrayIndexOutOfBoundsException() {
        //setup
        args = arrayOf("singleArg")

        //execute
        createArguementSets(args)
    }

    @Test(expected = NumberFormatException::class)
    fun invalidIntegerShouldThrowNumberFormatException() {
        //setup
        args = arrayOf("test.txt", "|", "4.5")

        //execute
        createArguementSets(args)
    }
}