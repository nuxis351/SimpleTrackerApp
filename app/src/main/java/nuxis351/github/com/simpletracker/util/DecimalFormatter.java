package nuxis351.github.com.simpletracker.util;

import java.text.DecimalFormat;

public class DecimalFormatter {

    public static double roundToTwoDecimals(double input) {
        return Math.round(input * 100.0) / 100.0;
    }

    public static double roundToOneDecimal(double input) {
        return Math.round(input * 10.0) / 10.0;
    }

    public static int removeDecimal(double input) {
        return (int) input;
    }

}
