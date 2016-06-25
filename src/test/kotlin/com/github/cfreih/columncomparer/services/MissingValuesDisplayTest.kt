package com.github.cfreih.columncomparer.services

import com.github.cfreih.columncomparer.dataclasses.ColumnInfo
import org.junit.Test
import kotlin.test.assertEquals

class MissingValuesDisplayTest {

    @Test
    fun emptyMissingValuesShouldOnlyBuildHeader() {
        //setup
        val missingValuesDisplay = MissingValuesDisplay(mapOf(), "test.txt")
        val expected = "Comparing files to test.txt...${System.lineSeparator()}"

        //execute
        val result : String = missingValuesDisplay.buildMissingValuesDisplay()

        //assert
        assertEquals(expected, result)
    }

    @Test
    fun singleItemInKeyValuePairShouldDisplay() {
        //setup
        val missingValuesDisplay = MissingValuesDisplay(
                mapOf(ColumnInfo("comparing-against.csv", listOf()) to listOf("missing1", "missing2")),
                "test.txt")
        val expected = StringBuilder("Comparing files to test.txt...").append(System.lineSeparator())
                            .append(System.lineSeparator())
                            .append("comparing-against.csv has 2 values different than test.txt:").append(System.lineSeparator())
                            .append("missing1").append(System.lineSeparator())
                            .append("missing2").append(System.lineSeparator())
                            .toString()

        //execute
        val result : String = missingValuesDisplay.buildMissingValuesDisplay()

        //assert
        assertEquals(expected, result)
    }

    @Test
    fun eachItemInKeyValuePairShouldDisplay() {
        //setup
        val missingValuesDisplay = MissingValuesDisplay(
                mapOf(ColumnInfo("comparing-against.csv", listOf()) to listOf("missing1", "missing2"),
                        ColumnInfo("second.txt", listOf()) to listOf("2missing1", "2missing2", "2missing3")),
                "test.txt")
        val expected = StringBuilder("Comparing files to test.txt...").append(System.lineSeparator())
                .append(System.lineSeparator())
                .append("comparing-against.csv has 2 values different than test.txt:").append(System.lineSeparator())
                .append("missing1").append(System.lineSeparator())
                .append("missing2").append(System.lineSeparator())
                .append(System.lineSeparator())
                .append("second.txt has 3 values different than test.txt:").append(System.lineSeparator())
                .append("2missing1").append(System.lineSeparator())
                .append("2missing2").append(System.lineSeparator())
                .append("2missing3").append(System.lineSeparator())
                .toString()

        //execute
        val result : String = missingValuesDisplay.buildMissingValuesDisplay()

        //assert
        assertEquals(expected, result)
    }
}
