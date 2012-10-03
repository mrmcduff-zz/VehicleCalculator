package com.mishmash.alpha.vehicleparts;

import java.util.List;
import com.mishmash.alpha.VehicleType;

/**
 * Class representing a VehicleFrame. Stores data and validates itself.
 */
public class VehicleFrame implements IVehiclePart, IDistanceModifierPart {

    public static final String PROPERTY_NAME = "Frames";
    private double timeModifierValue;
    private double speedModifierValue;
    private String name;
    private UnaryValidator validator = new UnaryValidator();

    /**
     * Standard constructor for a vehicle frame.
     * 
     * @param name
     * The name of the part (often used for display purposes).
     * 
     * @param timeModifierPercentage
     * How this frame affects the amount of time a vehicle can be ridden, expressed as a 
     * percentage. For instance, a value of -30 would decrease the amount of time by 30%.
     * 
     * @param speedModifierPercentage
     * How this frame affects the speed of a vehicle, with terms similar to timeModifierPercentage.
     * 
     * @param validTypes
     * The vehicles for which this type of frame is valid.
     */
    public VehicleFrame(String name, double timeModifierPercentage, double speedModifierPercentage,
            List<VehicleType> validTypes) {
        this.name = name;
        this.timeModifierValue = timeModifierPercentage;
        this.speedModifierValue = speedModifierPercentage;
        this.validator.setValidTypes(validTypes);
    }
    
    /**
     * Constructor for a frame that is valid on no types of vehicle.
     */
    public VehicleFrame(String name, double timeModifierPercentage, 
            double speedModifierPercentage) {
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
    public double getTimeModifierPercentage() {
        return this.timeModifierValue;
    }
    @Override
    public double getSpeedModifierPercentage() {
        return this.speedModifierValue;
    }

    @Override
    public boolean hasValidAttributes() {
        return this.timeModifierValue >= IDistanceModifierPart.MODIFIER_MINIMUM_PERCENTAGE &&
                this.speedModifierValue >= IDistanceModifierPart.MODIFIER_MINIMUM_PERCENTAGE && 
                this.name != null && !this.name.equals("");
    }
    
    /**
     * Two VehicleFrames are equal if and only if:
     * 1. They have the same name.
     * 2. They have the same modifier property values (time & speed)
     * 3. They have validators that are equal.
     */
    @Override
    public final boolean equals(Object other) {
        boolean answer = true;
        if (other instanceof VehicleFrame) {
            VehicleFrame otherFrame = (VehicleFrame) other;
            if (otherFrame.getName() != null) {
                answer = answer && (otherFrame.getName().equals(this.getName()));
            } else {
                answer = answer && this.getName() == null;
            }

            answer = answer && PartUtils.doubleEquals(otherFrame.getSpeedModifierPercentage(),
                    this.getSpeedModifierPercentage());
            answer = answer && PartUtils.doubleEquals(otherFrame.getTimeModifierPercentage(),
                    this.getTimeModifierPercentage());
            if (otherFrame.getValidator() != null) {
                answer = answer && otherFrame.getValidator().equals(this.validator);
            } else {
                answer = answer && (this.validator == null);
            }
            
        } else {
            answer = false;
        }
        return answer;
    }
    
    @Override
    public final int hashCode() {
        int hash = 0;
        if (this.getName() != null) {
            hash += this.getName().hashCode();
        }
        // Both magic numbers below are simply four digit primes, chosen
        // for no reason other than primality and magnitude.
        hash += (IDistanceModifierPart.SPEED_MODIFIER_KEY.hashCode() * 
                PartUtils.getHashableValueFromDouble(getSpeedModifierPercentage()));
        hash += (IDistanceModifierPart.TIME_MODIFIER_KEY.hashCode() * 
                PartUtils.getHashableValueFromDouble(getTimeModifierPercentage()));
        hash += this.validator.hashCode();
        
        return hash;
    }

    @Override
    public IVehicleTypeValidator getValidator() {
        return this.validator;
    }

}
