package com.github.cfreih.columncomparer.com.github.cfreih.columncomparer.input

import com.github.cfreih.columncomparer.input.ArgsVerifier
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class ArgsVerifierTest {

    lateinit var argsVerifier : ArgsVerifier

    @Before
    fun setup() {
        File("test.csv").createNewFile()
        File("test.txt").createNewFile()
    }

    @After
    fun tearDown() {
        File("test.csv").delete()
        File("test.txt").delete()
    }

    @Test
    fun tooFewButDivisibleByThreeArgumentsPassedShouldAddMessage() {
        //setup
        argsVerifier = ArgsVerifier(arrayOf("test1", "test2", "test3"))

        //execute
        argsVerifier.verifyNumberOfArguments()

        //assert
        assertEquals(1, argsVerifier.errorMessages.size)
        assertEquals(true, argsVerifier.errorMessages.contains("You have 3 arguments, but need at least six"))
    }

    @Test
    fun tooFewAndNotDivisibleByThreeArgumentsPassedShouldAddMessages() {
        //setup
        argsVerifier = ArgsVerifier(arrayOf("test1", "test2", "test3", "test4"))

        //execute
        argsVerifier.verifyNumberOfArguments()

        //assert
        assertEquals(2, argsVerifier.errorMessages.size)
        assertEquals(true, argsVerifier.errorMessages.contains("You have 4 arguments, but need at least six"))
        assertEquals(true, argsVerifier.errorMessages.contains("Arguments should be grouped in sets of three like \"fileName delimiter columnNumber\""))
    }

    @Test
    fun enoughAndDivisibleByThreeArgumentsPassedShouldAddNoMessages() {
        //setup
        argsVerifier = ArgsVerifier(arrayOf("test1", "test2", "test3", "test4", "test5", "test6"))

        //execute
        argsVerifier.verifyNumberOfArguments()

        //assert
        assertEquals(0, argsVerifier.errorMessages.size)
    }

    @Test
    fun enoughAndNotDivisibleByThreeArgumentsPassedShouldAddMessage() {
        //setup
        argsVerifier = ArgsVerifier(arrayOf("test1", "test2", "test3", "test4", "test5", "test6", "test7"))

        //execute
        argsVerifier.verifyNumberOfArguments()

        //assert
        assertEquals(1, argsVerifier.errorMessages.size)
        assertEquals(true, argsVerifier.errorMessages.contains("Arguments should be grouped in sets of three like \"fileName delimiter columnNumber\""))
    }

    @Test
    fun indexedArgumentIsNotFileShouldAddMessage() {
        //setup
        argsVerifier = ArgsVerifier(arrayOf("notAFile"))

        //execute
        argsVerifier.verifyArgumentIsFile(0)

        //assert
        assertEquals(1, argsVerifier.errorMessages.size)
        assertEquals(true, argsVerifier.errorMessages.contains("\"notAFile\" at index 0 is not a valid file or does not exist"))
    }

    @Test
    fun indexedArgumentIsFileShouldAddNoMessages() {
        //setup
        argsVerifier = ArgsVerifier(arrayOf("test.csv"))

        //execute
        argsVerifier.verifyArgumentIsFile(0)

        //assert
        assertEquals(0, argsVerifier.errorMessages.size)
    }

    @Test
    fun indexedArgumentIsNotIntegerShouldAddMessage() {
        //setup
        argsVerifier = ArgsVerifier(arrayOf("6.4"))

        //execute
        argsVerifier.verifyArgumentIsInteger(0)

        //assert
        assertEquals(1, argsVerifier.errorMessages.size)
        assertEquals(true, argsVerifier.errorMessages.contains("\"6.4\" at index 0 is not an integer"))
    }

    @Test
    fun indexedArgumentIsIntegerShouldAddNoMessages() {
        //setup
        argsVerifier = ArgsVerifier(arrayOf("6"))

        //execute
        argsVerifier.verifyArgumentIsInteger(0)

        //assert
        assertEquals(0, argsVerifier.errorMessages.size)
    }

    @Test(expected = IllegalArgumentException::class)
    fun tooFewArgumentsPassedShouldThrowIllegalArgumentException() {
        //setup
        argsVerifier = ArgsVerifier(arrayOf())

        //execute
        argsVerifier.verify()
    }

    @Test(expected = IllegalArgumentException::class)
    fun validArgumentsButNotSetOfThreeShouldThrowIllegalArgumentException() {
        //setup
        argsVerifier = ArgsVerifier(arrayOf("test.csv", "|", "4", "test.txt", ",", "8", "test.csv"))

        //execute
        argsVerifier.verify()
    }

    @Test(expected = IllegalArgumentException::class)
    fun notValidFileInArgumentsShouldThrowIllegalArgumentException() {
        //setup
        argsVerifier = ArgsVerifier(arrayOf("test.csv", "|", "4", "test.fail", ",", "8"))

        //execute
        argsVerifier.verify()
    }

    @Test(expected = IllegalArgumentException::class)
    fun notIntegerInArgumentsShouldThrowIllegalArgumentException() {
        //setup
        argsVerifier = ArgsVerifier(arrayOf("test.csv", "|", "4", "test.fail", ",", "eight"))

        //execute
        argsVerifier.verify()
    }

    @Test
    fun validArgumentsShouldVerifyArguments() {
        //setup
        argsVerifier = ArgsVerifier(arrayOf("test.csv", "|", "4", "test.txt", ",", "8"))

        //execute
        argsVerifier.verify()
    }

}