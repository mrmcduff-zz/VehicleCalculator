package com.mishmash.alpha.vehicleparts;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mishmash.alpha.VehicleType;

/**
 * A UnaryValidator cares only about the type of vehicle, and not
 * any additional arguments such as position.
 */
public class UnaryValidator implements IVehicleTypeValidator {

    private Map<VehicleType, Boolean> validMap = Maps.newHashMap();
    
    public UnaryValidator() {}
    
    /**
     * This is separated from the constructor so that the types can 
     * be set dynamically if needed.
     * 
     * @param validTypes
     * The vehicle types for which this is valid.
     */
    public void setValidTypes(List<VehicleType> validTypes) {
        if (validTypes != null) {
            for (VehicleType type : validTypes) {
                if (type != VehicleType.INVALID) {
                    validMap.put(type, true);
                }
            }
        }
    }
    
    @Override
    public boolean isValidForType(VehicleType type) {
        return validMap.containsKey(type) && validMap.get(type);
    }

    /**
     * A unary validator ignores the arguments.
     */
    @Override
    public boolean isValidForTypeWithParameters(VehicleType type,
            String... args) {
        return isValidForType(type);
    }
    
    public ImmutableMap<VehicleType, Boolean> getValidMap() {
        return ImmutableMap.copyOf(this.validMap);
    }
    
    /**
     * Validators are equal if they validate on the same types. The maps may not
     * be the same, as putting in what they don't validate doesn't change what they 
     * do validate.
     */
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
        int hash = VehicleType.INVALID.toString().hashCode();

        for (VehicleType type : this.getValidMap().keySet()) {
            if (this.isValidForType(type)) {
                hash += type.toString().hashCode();
            }
        }
        return hash;
    }

}
