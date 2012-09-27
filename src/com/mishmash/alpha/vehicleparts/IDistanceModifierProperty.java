package com.mishmash.alpha.vehicleparts;

public interface IDistanceModifierProperty {
    public double getSpeedModifierFactor();
    public double getTimeModifierFactor();
    public boolean hasAllValidModifiers();
}
