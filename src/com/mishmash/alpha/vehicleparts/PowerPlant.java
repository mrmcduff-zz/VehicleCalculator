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
    private static final String PROPERTY_NAME = "Powerplant";
    
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
    
}
