package com.github.cfreih.columncomparer.dataclasses

import com.github.cfreih.columncomparer.exceptions.FileReadException
import com.github.cfreih.columncomparer.services.populateFile
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class CreateColumnInfoTest {

    val fileName = "test.txt"
    val delimiter = ","
    val columnNumber = 3

    @Before
    fun setup() {
        File(fileName).createNewFile()
    }

    @After
    fun tearDown() {
        File(fileName).delete()
    }

    @Test
    fun emptyFileShouldProduceEmptyColumnInfoValues() {
        //setup
        val args = ArguementSet(fileName, delimiter, columnNumber)

        //execute
        val result : ColumnInfo = ColumnInfo.create(args)

        //assert
        assertEquals(fileName, result.fileName)
        assertEquals(0, result.columnValues.size)
    }

    @Test
    fun fileInCorrectFormatShouldReturnPopulatedColumnInfo() {
        //setup
        val args = ArguementSet(fileName, delimiter, columnNumber)
        populateFile(fileName, "testCol1,testCol2,testCol3,testCol4", "2testCol1,2testCol2,2testCol3,",
                "3TestCol1,,3testCol3,3testCol4")

        //execute
        val result : ColumnInfo = ColumnInfo.create(args)

        //assert
        assertEquals(fileName, result.fileName)
        assertEquals(3, result.columnValues.size)
        assertEquals(true, result.columnValues.contains("testCol4"))
        assertEquals(true, result.columnValues.contains(""))
        assertEquals(true, result.columnValues.contains("3testCol4"))
    }

    @Test(expected = FileReadException::class)
    fun tooFewColumnsShouldThrowException() {
        //setup
        val args = ArguementSet(fileName, delimiter, columnNumber)
        populateFile(fileName, "testCol1,testCol2,testCol3")

        //execute
        ColumnInfo.create(args)
    }

    @Test(expected = FileReadException::class)
    fun wrongDelimiterShouldThrowException() {
        //setup
        val args = ArguementSet(fileName, delimiter, columnNumber)
        populateFile(fileName, "testCol1|testCol2|testCol3|testCol4", "2testCol1|2testCol2|2testCol3|",
                "3TestCol1||3testCol3|3testCol4")

        //execute
        ColumnInfo.create(args)
    }
}