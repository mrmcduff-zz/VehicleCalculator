package com.mishmash.alpha.vehicleparts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.mishmash.alpha.VehicleType;

public abstract class UnaryVehicleProperty implements
        IVehiclePart {

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
    
    public ImmutableMap<VehicleType, Boolean> getValidMap() {
        return ImmutableMap.copyOf(this.validMap);
    }
    
    @Override
    public boolean equals(Object other) {
        boolean answer = true;
        if (other != null && other instanceof UnaryVehicleProperty) {
            UnaryVehicleProperty otherPart = (UnaryVehicleProperty) other;
            answer = answer && (otherPart.getName().equals(this.getName()));
            for (VehicleType type : VehicleType.values()) {
                // Using == for booleans on purpose here.
                answer = answer && ( otherPart.isValidOn(type) == this.isValidOn(type) );
                if (!answer) {
                    break;
                }
            }
        } else {
            answer = false;
        }
        return answer;
    }
    
    @Override
    public int hashCode() {
        int hash = this.getName().hashCode();

        for (VehicleType type : this.getValidMap().keySet()) {
            if (this.isValidOn(type)) {
                hash += type.hashCode();
            }
        }
        return hash;
    }
}
