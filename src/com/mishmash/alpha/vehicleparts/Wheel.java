package com.mishmash.alpha.vehicleparts;

import java.util.List;


import com.mishmash.alpha.PartPosition;
import com.mishmash.alpha.VehicleType;

/**
 * Represents a wheel. This contains data and is the most complicated of the parts
 * as it must keep a more complex type validator than other parts.
 */
public class Wheel implements IVehiclePart, IDistanceModifierPart {

    private String name;
    public static final String PROPERTY_NAME = "Wheels";
    private double timeModifierValue;
    private double speedModifierValue;
    
    /**
     * Used so other classes can determine the valid positions for this wheel.
     */
    private WheelPositionValidator positionValidator = new WheelPositionValidator();
    
    /**
     * Constructor, and the only way to set the characteristics of a Wheel.
     * 
     * @param name
     * A name for the type of wheel.
     * 
     * @param timeModifierPercentage
     * Amount, expressed as a 100-based percentage, that this wheel affects the 
     * time a rider can ride the vehicle. Values lower than -100 will create an invalid
     * wheel.
     * 
     * @param speedModifierPercentage
     * Amount, expressed as a 100-based percentage, that this wheel affects the 
     * speed of a vehicle. Values lower than -100 will create an invalid wheel.
     * 
     * @param orderedValidPositions
     * A set of lists of vehicle types, where each list corresponds to a position. For
     * instance, if two lists are passed, the first will be the vehicles for which
     * this wheel is valid as a FRONT wheel, and the second list will be the vehicles
     * for which this wheel is valid as a REAR wheel. The index is converted to a position
     * based on PartPosition.fromInt(index).
     */
    public Wheel(String name, double timeModifierPercentage, double speedModifierPercentage, List<VehicleType>... orderedValidPositions) {
        this.name = name;
        this.timeModifierValue = timeModifierPercentage;
        this.speedModifierValue = speedModifierPercentage;
        
        for (int i = 0; i < orderedValidPositions.length; ++i) {
            PartPosition temp = PartPosition.fromInt(i);
            if (temp != PartPosition.INVALID) {
                positionValidator.setValidTypesForPosition(temp, orderedValidPositions[i]);
            }
        }
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getPropertyName() {
        return Wheel.PROPERTY_NAME;
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
    public IVehicleTypeValidator getValidator() {
        return this.positionValidator;
    }
    
    
    @Override
    public boolean hasValidAttributes() {
        return this.timeModifierValue >= IDistanceModifierPart.MODIFIER_MINIMUM_PERCENTAGE &&
                this.speedModifierValue >= IDistanceModifierPart.MODIFIER_MINIMUM_PERCENTAGE && 
                this.name != null && !this.name.equals("");
    }
    
    /**
     * Two wheels are equal if:
     * 1. They have the same name.
     * 2. They have the same modifier properties (compared by doubleEquals)
     * 3. They are valid for the same types of vehicles in all possible positions.
     * 
     * Note that the validation maps don't necessarily have to be the same, and 
     * excess validation positions are ignored. These things are similarly ignored
     * in hashCode.
     */
    @Override
    public boolean equals(Object other) {
        boolean answer = true;
        if (other != null && other instanceof Wheel) {
            Wheel otherWheel = (Wheel) other;
            answer = answer && otherWheel.getName().equals(this.getName());
            answer = answer && 
                    PartUtils.doubleEquals(otherWheel.getSpeedModifierFactor(), 
                            this.getSpeedModifierFactor());
            answer = answer && 
                    PartUtils.doubleEquals(otherWheel.getTimeModifierFactor(), 
                            this.getTimeModifierFactor());
            if (otherWheel.getValidator() != null) {
                answer = answer && otherWheel.getValidator().equals(this.positionValidator);
            } else {
                answer = false;
            }
            
            
        } else {
            answer = false;
        }
        return answer;
    }
    
    @Override
    public int hashCode() {
        int hash = this.getName().hashCode();
        hash += (int) (IDistanceModifierPart.SPEED_MODIFIER_KEY.hashCode() *
                PartUtils.getHashableValueFromDouble(getSpeedModifierFactor()));
        hash += (int) (IDistanceModifierPart.TIME_MODIFIER_KEY.hashCode() *
                PartUtils.getHashableValueFromDouble(getTimeModifierFactor()));
        hash += this.positionValidator.hashCode();
        
        return hash;
    }



}
    

