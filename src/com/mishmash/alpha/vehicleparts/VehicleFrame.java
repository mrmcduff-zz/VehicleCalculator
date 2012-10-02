package com.mishmash.alpha.vehicleparts;

import java.util.List;
import com.mishmash.alpha.VehicleType;

public class VehicleFrame implements IVehiclePart, IDistanceModifierPart {

    public static final String PROPERTY_NAME = "Frames";
    private double timeModifierValue;
    private double speedModifierValue;
    private String name;
    private UnaryValidator validator = new UnaryValidator();

    public VehicleFrame(String name, double timeModifierPercentage, double speedModifierPercentage,
            List<VehicleType> validTypes) {
        this.name = name;
        this.timeModifierValue = timeModifierPercentage;
        this.speedModifierValue = speedModifierPercentage;
        this.validator.setValidTypes(validTypes);
    }
    
    public VehicleFrame(String name, double timeModifierPercentage, double speedModifierPercentage) {
        this(name, timeModifierPercentage, speedModifierPercentage, null);
    }
    
    @Override 
    public String getName() {
        return this.name;
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
        return this.timeModifierValue >= IDistanceModifierPart.MODIFIER_MINIMUM_PERCENTAGE && 
                this.speedModifierValue >= IDistanceModifierPart.MODIFIER_MINIMUM_PERCENTAGE;
    }
    
    /**
     * We start with the superclass's equals method. We are not in danger of breaking
     * transitivity because the superclass is abstract. This class is neither 
     * abstract nor final, so we protect the equals method with the final keyword.
     */
    @Override
    public final boolean equals(Object other) {
        boolean answer = true;
        if (other instanceof VehicleFrame) {
            VehicleFrame otherFrame = (VehicleFrame) other;
            answer = answer && (otherFrame.getName().equals(this.getName()));
            answer = answer && PartUtils.doubleEquals(otherFrame.getSpeedModifierFactor(),
                    this.getSpeedModifierFactor());
            answer = answer && PartUtils.doubleEquals(otherFrame.getTimeModifierFactor(),
                    this.getTimeModifierFactor());
            if (otherFrame.getValidator() != null) {
                answer = answer && otherFrame.getValidator().equals(this.validator);
            } else {
                answer = false;
            }
            
        } else {
            answer = false;
        }
        return answer;
    }
    
    @Override
    public final int hashCode() {
        int hash = this.getName().hashCode();
        // Both magic numbers below are simply four digit primes, chosen
        // for no reason other than primality and magnitude.
        hash += (int) (IDistanceModifierPart.SPEED_MODIFIER_KEY.hashCode() * 
                this.getSpeedModifierFactor());
        hash += (int) (IDistanceModifierPart.TIME_MODIFIER_KEY.hashCode() * 
                this.getTimeModifierFactor());
        hash += this.validator.hashCode();
        
        return hash;
    }

    @Override
    public IVehicleTypeValidator getValidator() {
        return this.validator;
    }

}
