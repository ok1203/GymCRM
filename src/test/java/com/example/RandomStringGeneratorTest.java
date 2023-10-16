package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomStringGeneratorTest {
    @Test
    public void testGenerateRandomStringWithValidLength() {
        int length = 10;
        String randomString = RandomStringGenerator.generateRandomString(length);

        assertNotNull(randomString);
        assertEquals(length, randomString.length());

        for (char c : randomString.toCharArray()) {
            assertTrue(Character.isLetterOrDigit(c));
        }
    }

    @Test
    public void testGenerateRandomStringWithZeroLength() {
        int length = 0;
        String randomString = RandomStringGenerator.generateRandomString(length);

        assertNotNull(randomString);
        assertEquals(length, randomString.length());
    }

    @Test
    public void testGenerateRandomStringWithLargeLength() {
        int length = 10000;
        String randomString = RandomStringGenerator.generateRandomString(length);

        assertNotNull(randomString);
        assertEquals(length, randomString.length());

        for (char c : randomString.toCharArray()) {
            assertTrue(Character.isLetterOrDigit(c));
        }
    }
}
