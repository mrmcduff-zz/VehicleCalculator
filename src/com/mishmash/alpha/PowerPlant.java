/**
 * 
 */
package com.mishmash.alpha;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mrmcduff
 *
 */
public class PowerPlant implements IVehicleProperty{
    
    private String name;
    private double speedInMph;
    private Map<VehicleType, Boolean> validMap = new HashMap<VehicleType, Boolean>();
    private static final String PROPERTY_NAME = "Power Plant";
    
    public PowerPlant(String name, double speedInMph) {
        this(name, speedInMph, null);
    }
    
    public PowerPlant(String name, double speedInMph, List<VehicleType> validTypes) {
        if (validTypes != null)
        {
            for (VehicleType type : validTypes) {
                setVehicleValidity(type, true);
            }
        }
        
        this.name = name;
        this.speedInMph = speedInMph;
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
    
    public boolean isValidOn(VehicleType type) {
        return this.validMap.containsKey(type) && this.validMap.get(type);
    }
    
    public void setVehicleValidity(VehicleType type, boolean isValid) {
        this.validMap.put(type, isValid);
    }
}
