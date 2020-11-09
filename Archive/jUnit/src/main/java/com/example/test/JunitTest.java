package com.example.test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

//@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JunitTest {
    /*
     jUnit creates separate instance of class for every test method
     This can be controlled by setting @TestInstance on class
     Static for BeforeALl is only needed if using TestInstance.Lifecycle.PER_CLASS
     EnabledOnOs will be disabled on other platforms
    */

    @Test
    @DisplayName("Basic")
    void testJunitTest_Normal() {
        // Assertions have a third argument for message
        assertEquals("123", "123", "your message here");
        assertFalse(false, "Checks if the output is false");
        assertTrue(true, "Checks if the output is true");
//        Assertions.assertNotEquals();
//        Assertions.assertArrayEquals();
//        Assertions.assertIterableEquals();
//        Assertions.fail();
    }

    @BeforeEach
    void testJunitTest_BeforeEach(TestInfo testInfo, TestReporter testReporter) {
//        System.out.println(testInfo);  // for getting info
//        System.out.println(testReporter);  // for setting info
        testReporter.publishEntry(testInfo.getTestMethod().get().getName());
    }

    @AfterEach
    void testJunitTest_AfterEach() {
    }


    @BeforeAll
    static void testJunitTest_BeforeAll() {
    }

    @AfterAll
    static void testJunitTest_AfterAll() {
    }

    @Test
    @Tag("Repeated")
    void testJunitTest_Exception() {
        assertThrows(NumberFormatException.class, () -> Integer.parseInt("one"), "well");
    }

    @Test
    @Tag("Repeated")
        // to run tests based on tags
    void testJunitTest_Performance() {
        int[] a = {12, 125, 1};
        for (int i = 1; i <= 1000; i++) {
            a[0] = i;
            Arrays.sort(a);
        }
    }

    @Test
    @DisplayName("Failing Test")
//    @Disabled
    @EnabledOnOs(OS.WINDOWS)
    @EnabledOnJre(JRE.OTHER)
//    @EnabledIf("")
//    @EnabledIfSystemProperty()
//    @EnabledIfEnvironmentVariable()
    void testJunitTest_Fail() {
        // to test based on the assumption unlike assert it is used for handling test
        assumeTrue(() -> true);
        fail("Test failed");
        assertAll(() -> assertTrue(true), () -> assertFalse(false));
        assertFalse(true, () -> "Lazy init of string, the string only inits when the assertion fails");
    }

    @Nested
    @DisplayName("Nested")
    class grouped {
        // if one of the child method fails the whole nested class fails
        @Test
        void testOne() {
            fail("Failed");
        }

        @RepeatedTest(3)
        void testTwo(RepetitionInfo repetitionInfo) {
            System.out.println(repetitionInfo);
        }
    }


}
