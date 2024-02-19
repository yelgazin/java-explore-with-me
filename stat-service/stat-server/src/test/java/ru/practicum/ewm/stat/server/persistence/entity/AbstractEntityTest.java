package ru.practicum.ewm.stat.server.persistence.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AbstractEntityTest {

    static class TestClass extends AbstractEntity {
    }

    private final TestClass testClass1 = new TestClass();
    private final TestClass testClass2 = new TestClass();

    @Test
    void equals_whenTwoObjectHasTheSameId_thenReturnedTrue() {
        testClass1.setId(1L);
        testClass2.setId(1L);

        boolean equals = testClass1.equals(testClass2);

        assertTrue(equals);
    }

    @Test
    void equals_whenTwoObjectHasNullId_thenReturnedFalse() {
        testClass1.setId(null);
        testClass2.setId(null);

        boolean equals = testClass1.equals(testClass2);

        assertFalse(equals);
    }
}