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
public class PowerPlant extends UnaryVehicleProperty{
    
    private double speedInMph;
    public static final String PROPERTY_NAME = "Powerplant";
    public static final String SPEED_IN_MPH_KEY = "SpeedInMPH";
    
    public PowerPlant(String name, double speedInMph) {
        this(name, speedInMph, null);
    }
    
    public PowerPlant(String name, double speedInMph, List<VehicleType> validTypes) {
        super(name, validTypes);
        this.speedInMph = speedInMph;
    }
    
    @Override
    public String getPropertyName() {
        return PowerPlant.PROPERTY_NAME;
    }
    
    public double getSpeedInMph() {
        return this.speedInMph;
    }
    
    @Override
    public final boolean equals(Object other) {
        boolean answer = super.equals(other);
        if (other instanceof PowerPlant) {
            PowerPlant otherPlant = (PowerPlant) other;
            answer = answer && 
                    (PartUtils.doubleEquals(otherPlant.getSpeedInMph(), 
                            this.getSpeedInMph()));
        } else {
            answer = false;
        }
        return answer;
    }
    
    @Override
    public final int hashCode() {
        int hash = super.hashCode();
        hash += (int) (PowerPlant.SPEED_IN_MPH_KEY.hashCode() * this.speedInMph);
        return hash;
    }
    
}
