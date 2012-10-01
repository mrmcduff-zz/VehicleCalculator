package com.mishmash.alpha;

import java.util.List;

import com.google.common.collect.Lists;

public enum VehicleType {
    BICYCLE, TRICYCLE, SCOOTER, INVALID;

    public static final List<String> VALID_TYPE_STRINGS = 
            Lists.newArrayList(BICYCLE.toString(), TRICYCLE.toString(), SCOOTER.toString());
    
    @Override
    public String toString() {
        switch(this) {
        case BICYCLE:
            return "Bicycle";
        case TRICYCLE:
            return "Tricycle";
        case SCOOTER:
            return "Scooter";
        default:
            return "Unknown type";
        }
    }
    
    public static VehicleType fromString(String s) {
        VehicleType typeFromString = INVALID;
        for (VehicleType type : VehicleType.values()) {
            if (type.toString().equals(s)) {
                typeFromString = type;
                break;
            }
        }
        return typeFromString;
    }
    
    public static VehicleType[] validValues() {
        return new VehicleType[] {BICYCLE, TRICYCLE, SCOOTER};
    }
    
}
