package com.mishmash.alpha.vehicleparts;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.mishmash.alpha.VehicleType;

public class WheelPositionValidator implements IVehicleTypeValidator {

    private List<Map<VehicleType, Boolean>> tirePositionMapList = Lists.newArrayList();
    
    public WheelPositionValidator() {
        
    }
    @Override
    public boolean isValidForType(VehicleType type) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isValidForTypeWithParameters(VehicleType type,
            String... args) {
        boolean isValid = false;
        if(args.length > 0) {
            
        } 
        return isValid;
    }

}
