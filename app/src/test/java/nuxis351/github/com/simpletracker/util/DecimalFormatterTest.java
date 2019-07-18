package nuxis351.github.com.simpletracker.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DecimalFormatterTest {

    @Test
    public void roundToTwoDecimalsTest() {
        double input = 25.25252525;
        double expectedResult = 25.25;

        double result = DecimalFormatter.roundToTwoDecimals(input);
        System.out.println("Expected: " + expectedResult + " \nActual: " + result);
        assertEquals(expectedResult, result, 0);
    }

    @Test
    public void roundToOneDecimalTest() {
        double input = 45.4545454545;
        double expectedResult = 45.5;

        double result = DecimalFormatter.roundToOneDecimal(input);
        System.out.println("Expected: " + expectedResult + " \nActual: " + result);
        assertEquals(expectedResult, result, 0);
    }

    @Test
    public void removeDecimalTest() {
        double input = 24.2351251;
        int expectedResult = 24;

        int result = DecimalFormatter.removeDecimal(input);
        System.out.println("Expected: " + expectedResult + " \nActual: " + result);
        assertEquals(expectedResult, result, 0);
    }

}
