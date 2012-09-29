package com.mishmash.alpha.vehicleparts;

import com.mishmash.alpha.VehicleType;

public interface IVehicleTypeValidator {
    public boolean isValidForType(VehicleType type);
    public boolean isValidForTypeWithParameters(VehicleType type, String...args);
}
