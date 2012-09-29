package com.mishmash.alpha.vehicleparts;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mishmash.alpha.VehicleType;

public class UnaryValidator implements IVehicleTypeValidator {

    private Map<VehicleType, Boolean> validMap = Maps.newHashMap();
    
    public UnaryValidator() {}
    
    public void setValidTypes(List<VehicleType> validTypes) {
        if (validTypes != null) {
            for (VehicleType type : validTypes) {
                validMap.put(type, true);
            }
        }

    }
    
    @Override
    public boolean isValidForType(VehicleType type) {
        return validMap.containsKey(type) && validMap.get(type);
    }

    @Override
    public boolean isValidForTypeWithParameters(VehicleType type,
            String... args) {
        return isValidForType(type);
    }
    
    public ImmutableMap<VehicleType, Boolean> getValidMap() {
        return ImmutableMap.copyOf(this.validMap);
    }
    
    @Override
    public boolean equals(Object other) {
        boolean answer = true;
        if (other != null && other instanceof UnaryValidator) {
            UnaryValidator otherValidator = (UnaryValidator) other;
            
            for (VehicleType type : VehicleType.values()) {
                // Using == for booleans on purpose here.
                answer = answer && ( otherValidator.isValidForType(type) == this.isValidForType(type) );
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
        int hash = (validMap.size() + 1) * VehicleType.INVALID.hashCode();

        for (VehicleType type : this.getValidMap().keySet()) {
            if (this.isValidForType(type)) {
                hash += type.hashCode();
            }
        }
        return hash;
    }

}
