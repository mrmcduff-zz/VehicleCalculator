package com.mishmash.alpha.vehicleparts;

public interface IDistanceModifierProperty {
    public static final String TIME_MODIFIER_KEY = "TimeModifierPercentage";
    public static final String SPEED_MODIFIER_KEY = "SpeedModifierPercentage";
    
    public double getSpeedModifierFactor();
    public double getTimeModifierFactor();
    public boolean hasAllValidModifiers();
}
