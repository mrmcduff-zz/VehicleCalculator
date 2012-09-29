package com.mishmash.alpha.vehicleparts;

import java.util.Map;
import com.google.common.collect.Maps;
import com.mishmash.alpha.VehicleType;

public class UnaryValidator implements IVehicleTypeValidator {

    private Map<VehicleType, Boolean> validMap = Maps.newHashMap();
    
    @Override
    public boolean isValidForType(VehicleType type) {
        return validMap.containsKey(type) && validMap.get(type);
    }

    @Override
    public boolean isValidForTypeWithParameters(VehicleType type,
            String... args) {
        return isValidForType(type);
    }

}
