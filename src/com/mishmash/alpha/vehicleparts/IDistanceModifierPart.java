package com.mishmash.alpha.vehicleparts;

/**
 * Interface implemented by any part that has a speed and time percentage modifier 
 * effect on a vehicle.
 */
public interface IDistanceModifierPart {
    public static final String TIME_MODIFIER_KEY = "TimeModifierPercentage";
    public static final String SPEED_MODIFIER_KEY = "SpeedModifierPercentage";
    public static final double MODIFIER_MINIMUM_PERCENTAGE = -100;
    
    public double getSpeedModifierFactor();
    public double getTimeModifierFactor();
    public boolean hasAllValidModifiers();
}
