package com.example.juniteg.service;
// CalculatorTest.java
import com.example.juniteg.service.CalculatorService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorServiceTest {

    @Test
    public void testAdd() {
        // Arrange
        CalculatorService calculator = new CalculatorService();
        int a = 3;
        int b = 2;
        int expected = 5;

        // Act
        int result = calculator.add(a, b);

        // Assert
        assertEquals(expected, result, "2 + 3 should equal 5");
    }
}