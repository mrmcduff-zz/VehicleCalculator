package com.mishmash.alpha.vehicleparts;

import java.util.List;


import com.mishmash.alpha.PartPosition;
import com.mishmash.alpha.VehicleType;

public class Wheel implements IVehiclePart, IDistanceModifierPart {

    private String name;
    public static final String PROPERTY_NAME = "Wheels";
    private double timeModifierValue;
    private double speedModifierValue;
    private WheelPositionValidator positionValidator = new WheelPositionValidator();
    
    public Wheel(String name, double timeModifierPercentage, double speedModifierPercentage, List<VehicleType>... orderedValidPositions) {
        this.name = name;
        this.timeModifierValue = PartUtils.convertFromPercentageToModifier(timeModifierPercentage);
        this.speedModifierValue = PartUtils.convertFromPercentageToModifier(speedModifierPercentage);
        
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
    public boolean hasAllValidModifiers() {
        return this.timeModifierValue > 0 && this.speedModifierValue > 0;
    }
    
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
                this.getSpeedModifierFactor());
        hash += (int) (IDistanceModifierPart.TIME_MODIFIER_KEY.hashCode() *
                this.getTimeModifierFactor());
        hash += this.positionValidator.hashCode();
        
        return hash;
    }



}
    

