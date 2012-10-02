package com.mishmash.alpha.vehicleparts;

public class PartUtils {
    public final static double PERCENTAGE_CONVERTER = 100.0;
    
    public static double convertFromPercentageToMultiplicativeModifier(double percentage) {
        return 1.0 + (percentage / PERCENTAGE_CONVERTER);
    }
    
    public static double convertFromPercentageToAdditiveModifier(double percentage) {
        return percentage/PERCENTAGE_CONVERTER;
    }
    
    
    public static boolean doubleEquals(double first, double second) {
        return Math.abs(first - second) < IVehiclePart.DELTA;
    }

}
