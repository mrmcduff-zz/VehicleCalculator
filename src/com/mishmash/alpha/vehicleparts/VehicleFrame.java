package com.mishmash.alpha.vehicleparts;

import java.util.List;

import com.mishmash.alpha.VehicleType;

public class VehicleFrame extends UnaryVehicleProperty implements IDistanceModifierProperty {

    private static final String PROPERTY_NAME = "Frames";
    private double timeModifierValue;
    private double speedModifierValue;

    public VehicleFrame(String name, double timeModifierPercentage, double speedModifierPercentage,
            List<VehicleType> validTypes) {
        super(name, validTypes);
        this.timeModifierValue = PropertyUtilities.convertFromPercentageToModifier(timeModifierPercentage);
        this.speedModifierValue = PropertyUtilities.convertFromPercentageToModifier(speedModifierPercentage);
    }
    
    public VehicleFrame(String name, double timeModifierPercentage, double speedModifierPercentage) {
        this(name, timeModifierPercentage, speedModifierPercentage, null);
    }
    
    @Override
    public String getPropertyName() {
        return VehicleFrame.PROPERTY_NAME;
    }
    
    @Override
    public double getTimeModifierFactor() {
        return this.timeModifierValue;
    }
    @Override
    public double getSpeedModifierFactor() {
        return this.speedModifierValue;
    }

    @Override
    public boolean hasAllValidModifiers() {
        return this.timeModifierValue > 0 && this.speedModifierValue > 0;
    }
}
