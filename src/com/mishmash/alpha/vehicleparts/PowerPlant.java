/**
 * 
 */
package com.mishmash.alpha.vehicleparts;

import java.util.List;
import com.mishmash.alpha.VehicleType;

/**
 * Class representing a vehicle power plant. Stores all relevant data and has
 * the ability to check its own validity based on data and type.
 */
public class PowerPlant implements IVehiclePart{
    
    private double speedInMph;
    private String name;
    public static final String PROPERTY_NAME = "Powerplant";
    public static final String SPEED_IN_MPH_KEY = "SpeedInMPH";
    private UnaryValidator validator = new UnaryValidator();
    
    public PowerPlant(String name, double speedInMph) {
        this(name, speedInMph, null);
    }
    
    public PowerPlant(String name, double speedInMph, List<VehicleType> validTypes) {
        this.name = name;
        this.speedInMph = speedInMph;
        this.validator.setValidTypes(validTypes);
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getPropertyName() {
        return PowerPlant.PROPERTY_NAME;
    }
    
    public double getSpeedInMph() {
        return this.speedInMph;
    }
    
    @Override
    public IVehicleTypeValidator getValidator() {
        return this.validator;
    }
    
    @Override
    public boolean hasValidAttributes() {
        return (name != null && !name.equals("") && speedInMph >= 0);
    }
    
    /**
     * Two PowerPlants are equal if they have the same name, speed, and 
     * have validators that are equal.
     */
    @Override
    public final boolean equals(Object other) {
        boolean answer = true;
        if (other instanceof PowerPlant) {
            PowerPlant otherPlant = (PowerPlant) other;
            if (otherPlant.getName() == null) {
                answer = answer && this.getName() == null;
            } else {
                answer = answer && (otherPlant.getName().equals(this.getName()));
            }
            
            answer = answer && 
                    (PartUtils.doubleEquals(otherPlant.getSpeedInMph(), 
                            this.getSpeedInMph()));
            if (otherPlant.getValidator() != null) {
                answer = answer && otherPlant.getValidator().equals(this.validator);
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
        int hash = 0;
        if (this.getName() != null) {
            hash = this.getName().hashCode();
        }
        
        hash += (PowerPlant.SPEED_IN_MPH_KEY.hashCode() * 
                PartUtils.getHashableValueFromDouble(speedInMph));
        hash += this.validator.hashCode();
        return hash;
    }
    
}
