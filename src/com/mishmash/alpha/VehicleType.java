package com.mishmash.alpha;

import java.util.List;
import com.google.common.collect.Lists;

/**
 * Enum representing the types of vehicles available.
 */
public enum VehicleType {
    BICYCLE, TRICYCLE, SCOOTER, INVALID;

    /**
     * A convenient list to iterate over.
     */
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
    
    /**
     * Our data is all string-based, so it's helpful to be
     * able to convert from a string to an enum as well.
     * 
     * @param s
     * The string to be converted.
     * 
     * @return
     * A value representing the string, or INVALID if no such value exists.
     */
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
    
    /**
     * Getter for the valid values. Used for combobox models.
     * 
     * @return
     * An array of the valid values.
     */
    public static VehicleType[] validValues() {
        return new VehicleType[] {BICYCLE, TRICYCLE, SCOOTER};
    }
    
}
