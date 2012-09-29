package com.mishmash.alpha.vehicleparts;

import java.util.List;
import com.mishmash.alpha.VehicleType;

public class VehicleFrame extends UnaryVehicleProperty implements IDistanceModifierPart {

    public static final String PROPERTY_NAME = "Frames";
    private double timeModifierValue;
    private double speedModifierValue;

    public VehicleFrame(String name, double timeModifierPercentage, double speedModifierPercentage,
            List<VehicleType> validTypes) {
        super(name, validTypes);
        this.timeModifierValue = PartUtils.convertFromPercentageToModifier(timeModifierPercentage);
        this.speedModifierValue = PartUtils.convertFromPercentageToModifier(speedModifierPercentage);
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
    
    /**
     * We start with the superclass's equals method. We are not in danger of breaking
     * transitivity because the superclass is abstract. This class is neither 
     * abstract nor final, so we protect the equals method with the final keyword.
     */
    @Override
    public final boolean equals(Object other) {
        boolean answer = super.equals(other);
        if (other instanceof VehicleFrame) {
            VehicleFrame otherFrame = (VehicleFrame) other;
//            answer = answer && (otherFrame.getName().equals(this.getName()));
            answer = answer && PartUtils.doubleEquals(otherFrame.getSpeedModifierFactor(),
                    this.getSpeedModifierFactor());
            answer = answer && PartUtils.doubleEquals(otherFrame.getTimeModifierFactor(),
                    this.getTimeModifierFactor());
        } else {
            answer = false;
        }
        return answer;
    }
    
    @Override
    public final int hashCode() {
        int hash = super.hashCode();
        // Both magic numbers below are simply four digit primes, chosen
        // for no reason other than primality and magnitude.
        hash += (int) (IDistanceModifierPart.SPEED_MODIFIER_KEY.hashCode() * 
                this.getSpeedModifierFactor());
        hash += (int) (IDistanceModifierPart.TIME_MODIFIER_KEY.hashCode() * 
                this.getTimeModifierFactor());
        return hash;
    }

}
