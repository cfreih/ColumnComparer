package com.github.cfreih.columncomparer.services

import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class CreateColumnComparerTest {

    companion object {

        val fileName1 = "test.txt"
        val fileName2 = "test.csv"

        @JvmStatic
        @BeforeClass
        fun setup() {
            File(fileName1).createNewFile()
            populateFile(fileName1, "testCol1|testCol2|testCol3|testCol4", "2testCol1|2testCol2|2testCol3|",
                    "3TestCol1||3testCol3|3testCol4")
            File(fileName2).createNewFile()
            populateFile(fileName2, "testCol1,testCol2,testCol3,testCol4", "2testCol1,2testCol2,2testCol3,",
                    "3TestCol1,,3testCol3,3testCol4")
        }

        @JvmStatic
        @AfterClass
        fun tearDown() {
            File(fileName1).delete()
            File(fileName2).delete()
        }

    }

    @Test
    fun validStringArrayShouldCreateColumnComparer() {
        //setup
        val args = arrayOf("test.txt", "|", "2", "test.csv", ",", "2")

        //execute
        val result = ColumnComparer.create(args)

        //assert
        assertEquals("test.txt", result.masterColumn.fileName)
        assertEquals(3, result.masterColumn.columnValues.size)
        assertEquals(true, result.masterColumn.columnValues.contains("testCol3"))
        assertEquals(true, result.masterColumn.columnValues.contains("2testCol3"))
        assertEquals(true, result.masterColumn.columnValues.contains("3testCol3"))

        assertEquals(1, result.comparingColumns.size)
        assertEquals("test.csv", result.comparingColumns[0].fileName)
        assertEquals(3, result.comparingColumns[0].columnValues.size)
        assertEquals(true, result.comparingColumns[0].columnValues.contains("testCol3"))
        assertEquals(true, result.comparingColumns[0].columnValues.contains("2testCol3"))
        assertEquals(true, result.comparingColumns[0].columnValues.contains("3testCol3"))
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun emptyArrayPassedShouldReturn() {
        //execute
        ColumnComparer.create(arrayOf())
    }
}