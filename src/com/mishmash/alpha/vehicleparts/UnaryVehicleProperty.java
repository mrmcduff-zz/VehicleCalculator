package com.mishmash.alpha.vehicleparts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mishmash.alpha.VehicleType;

public abstract class UnaryVehicleProperty implements
        IVehicleProperty {

    private String name;
    private Map<VehicleType, Boolean> validMap = new HashMap<VehicleType, Boolean>();
    
    public UnaryVehicleProperty(String name) {
        this(name, null);
    }
    
    public UnaryVehicleProperty(String name, List<VehicleType> validTypes) {
        if (validTypes != null)
        {
            for (VehicleType type : validTypes) {
                setVehicleValidity(type, true);
            }
        }
        
        this.name = name;
    }
    
    @Override
    public String getName() {
        return this.name;
    }

    public boolean isValidOn(VehicleType type) {
        return this.validMap.containsKey(type) && this.validMap.get(type);
    }
    
    public void setVehicleValidity(VehicleType type, boolean isValid) {
        this.validMap.put(type, isValid);
    }
}
