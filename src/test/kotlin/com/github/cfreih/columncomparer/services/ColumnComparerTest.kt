package com.github.cfreih.columncomparer.services

import com.github.cfreih.columncomparer.dataclasses.ColumnInfo
import org.junit.Test
import kotlin.test.assertEquals

class ColumnComparerTest {

    @Test
    fun noComparingColumnsShouldReturnEmptyMap() {
        //setup
        val masterColumn = ColumnInfo("master.txt", listOf("test1", "test2", "test3", "test4"))
        val columnComparer = ColumnComparer(masterColumn, listOf())

        //execute
        val result : Map<ColumnInfo, List<String>> = columnComparer.findMissingValues()

        //assert
        assertEquals(0, result.size)
    }

    @Test
    fun noValuesInMasterOrComparingColumnsShouldReturnEmptyLists() {
        //setup
        val masterColumn = ColumnInfo("master.txt", listOf())
        val comparingColumn1 = ColumnInfo("compare1.txt", listOf())
        val comparingColumn2 = ColumnInfo("compare2.txt", listOf())
        val columnComparer = ColumnComparer(masterColumn, listOf(comparingColumn1, comparingColumn2))

        //execute
        val result : Map<ColumnInfo, List<String>> = columnComparer.findMissingValues()

        //assert
        assertEquals(true, result.containsKey(comparingColumn1))
        assertEquals(0, result.get(comparingColumn1)?.size)
        assertEquals(true, result.containsKey(comparingColumn2))
        assertEquals(0, result.get(comparingColumn2)?.size)
    }

    @Test
    fun noMissingValuesFromMasterShouldReturnColumnInfoWithEmptyLists() {
        //setup
        val masterColumn = ColumnInfo("master.txt", listOf("test1", "test2", "test3", "test4"))
        val comparingColumn1 = ColumnInfo("compare.txt", listOf("test1", "test3"))
        val comparingColumn2 = ColumnInfo("compare2.txt", listOf("test2", "test3"))
        val columnComparer = ColumnComparer(masterColumn, listOf(comparingColumn1, comparingColumn2))

        //execute
        val result : Map<ColumnInfo, List<String>> = columnComparer.findMissingValues()

        //assert
        assertEquals(true, result.containsKey(comparingColumn1))
        assertEquals(0, result.get(comparingColumn1)?.size)
        assertEquals(true, result.containsKey(comparingColumn2))
        assertEquals(0, result.get(comparingColumn2)?.size)
    }

    @Test
    fun missingValuesInColumnInfoShouldBePutInMap() {
        //setup
        val masterColumn = ColumnInfo("master.txt", listOf("test1", "test2", "test3", "test4"))
        val comparingColumn1 = ColumnInfo("compare.txt", listOf("test10", "test30"))
        val comparingColumn2 = ColumnInfo("compare2.txt", listOf("2test2", "test3"))
        val columnComparer = ColumnComparer(masterColumn, listOf(comparingColumn1, comparingColumn2))

        //execute
        val result : Map<ColumnInfo, List<String>> = columnComparer.findMissingValues()

        //assert
        assertEquals(true, result.containsKey(comparingColumn1))
        assertEquals(2, result.get(comparingColumn1)?.size)
        assertEquals(true, result.get(comparingColumn1)?.contains("test10"))
        assertEquals(true, result.get(comparingColumn1)?.contains("test30"))
        assertEquals(true, result.containsKey(comparingColumn2))
        assertEquals(1, result.get(comparingColumn2)?.size)
        assertEquals(true, result.get(comparingColumn2)?.contains("2test2"))
    }
}
