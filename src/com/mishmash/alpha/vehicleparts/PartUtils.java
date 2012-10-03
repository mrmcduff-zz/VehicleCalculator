package com.mishmash.alpha.vehicleparts;

/**
 * Useful utility functions for vehicles and vehicle parts.
 */
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
    
    /**
     * Function is required by VehicleParts to make .equals work properly.
     * Two equal objects should have the same hashcode, and their double
     * attributes need to be a part of that hashcode. However, if two doubles
     * are unequal but close enough to pass the doubleEquals function above
     * the hashcodes could be different while the objects are equal.
     * 
     * @param originalValue
     * The value to be rounded.
     * 
     * @return
     * A value rounded to the precision of IVehiclePart.DELTA.
     */
    public static double roundToDelta(double originalValue) {
        long chopped = (long) (originalValue / IVehiclePart.DELTA);
        return (double) chopped * IVehiclePart.DELTA;
    }
    
    /**
     * Function is required by VehicleParts to make .equals work properly.
     * Two equal objects should have the same hashcode, and their double
     * attributes need to be a part of that hashcode. However, if two doubles
     * are unequal but close enough to pass the doubleEquals function above
     * the hashcodes could be different while the objects are equal.
     * 
     * We achieve this goal by rounding at the fifth decimal place 
     * (actually, just two higher than the DELTA, so changes to the delta
     * will not affect this function's validity), thereby ensuring that equal
     * things have equal hashcodes.
     * 
     * @param originalValue
     * The value to be rounded.
     * 
     * @return
     * A value that reflects only as much precision as was used to calculate
     * equals in doubleEquals above.
     */
    public static int getHashableValueFromDouble(double originalValue) {
        double higherThanDelta = IVehiclePart.DELTA * 100;
        // Remember that dividing by IVehiclePart.DELTA increases
        // the magnitude of a number by seven factors of ten.
        if (originalValue < higherThanDelta * Integer.MAX_VALUE &&
                originalValue > higherThanDelta * Integer.MIN_VALUE) {
            return (int) (Math.round(originalValue / higherThanDelta)) ;
        } else if (originalValue > higherThanDelta * Integer.MAX_VALUE){
            // This is the case where our double value is too big.
            return Integer.MAX_VALUE;
        } else {
            // This is the case where our double value is too small (really
            // too big of a negative number
            return Integer.MIN_VALUE;
        }
        
    }

}
