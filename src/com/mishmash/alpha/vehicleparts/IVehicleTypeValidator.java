package com.mishmash.alpha.vehicleparts;

import com.mishmash.alpha.VehicleType;

/**
 * Interface for type validator objects. All IVehicleParts use these to store whether or
 * not they are valid for the various types of vehicles and for various arguments (such as
 * position).
 */
public interface IVehicleTypeValidator {
    public boolean isValidForType(VehicleType type);
    public boolean isValidForTypeWithParameters(VehicleType type, String...args);
}
