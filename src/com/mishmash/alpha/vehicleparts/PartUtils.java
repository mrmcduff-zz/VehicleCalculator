package com.mishmash.alpha.vehicleparts;

public class PartUtils {
    public final static double PERCENTAGE_CONVERTER = 100.0;
    
    public static double convertFromPercentageToModifier(double percentage) {
        double convertedValue = 0.0;
        if (percentage >= 0) {
            convertedValue = percentage / PERCENTAGE_CONVERTER;
        } else {
            convertedValue = 1.0 - (percentage / PERCENTAGE_CONVERTER);
        }
        return convertedValue;
    }
    
    public static boolean doubleEquals(double first, double second) {
        return Math.abs(first - second) < IVehiclePart.DELTA;
    }

}
