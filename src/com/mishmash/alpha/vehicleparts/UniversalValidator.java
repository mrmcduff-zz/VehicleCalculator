package com.mishmash.alpha.vehicleparts;

import com.mishmash.alpha.VehicleType;

/**
 * A UniversalValidator is valid on all types of vehicles with
 * all parameters.
 */
public class UniversalValidator implements IVehicleTypeValidator {

    /**
     * This always returns true if the vehicle type is valid.
     */
    @Override
    public boolean isValidForType(VehicleType type) {
        return type != VehicleType.INVALID;
    }

    /**
     * The args here don't matter. This returns true if the type is valid.
     */
    @Override
    public boolean isValidForTypeWithParameters(VehicleType type,
            String... args) {
        return type != VehicleType.INVALID;
    }
    
    /**
     * Because there are no defining attributes, all UniversalValidators
     * must be equal.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof UniversalValidator) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Because all UniversalValidators are equal, they must have
     * the same hashCode.
     */
    @Override
    public int hashCode() {
        return UniversalValidator.class.toString().hashCode();
    }

}
