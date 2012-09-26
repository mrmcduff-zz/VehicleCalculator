package com.mishmash.alpha;

public enum VehicleType {
    BICYCLE, TRICYCLE, SCOOTER;
    
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

}
