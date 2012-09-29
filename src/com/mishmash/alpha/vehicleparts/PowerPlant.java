/**
 * 
 */
package com.mishmash.alpha.vehicleparts;

import java.util.List;
import com.mishmash.alpha.VehicleType;

/**
 * @author mrmcduff
 *
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
    public final boolean equals(Object other) {
        boolean answer = true;
        if (other instanceof PowerPlant) {
            PowerPlant otherPlant = (PowerPlant) other;
            answer = answer && (otherPlant.getName().equals(this.getName()));
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
        int hash = this.getName().hashCode();
        hash += (int) (PowerPlant.SPEED_IN_MPH_KEY.hashCode() * this.speedInMph);
        hash += this.validator.hashCode();
        return hash;
    }
    
}
