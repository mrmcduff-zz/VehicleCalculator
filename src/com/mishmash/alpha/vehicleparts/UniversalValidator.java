package com.mishmash.alpha.vehicleparts;

import com.mishmash.alpha.VehicleType;

public class UniversalValidator implements IVehicleTypeValidator {

    @Override
    public boolean isValidForType(VehicleType type) {
        return true;
    }

    @Override
    public boolean isValidForTypeWithParameters(VehicleType type,
            String... args) {
        return true;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof UniversalValidator) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        return UniversalValidator.class.toString().hashCode();
    }

}
