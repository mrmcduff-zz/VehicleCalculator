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

}
