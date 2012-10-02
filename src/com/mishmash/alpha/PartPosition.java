package com.mishmash.alpha;

/**
 * Enum representing positions on the car. In this case, we have only FRONT and REAR
 * (applied to wheels), but we could increase the number of posistions relatively easily
 * by editing this enum.
 */
public enum PartPosition {
    FRONT(0), REAR(1), INVALID(-1);
    
    private int value;
    
    /**
     * Private constructor, as required for an enumeration.
     * We use this position numerically sometimes, so it 
     * behooves us to define exactly what numbers they
     * align with.
     * 
     * @param value
     * The numeric value of the position.
     */
    private PartPosition(int value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        switch(this) {
        case FRONT:
            return "Front";
        case REAR:
            return "Rear";
        default:
            return "Unknown Location";
        }
    }
    
    /**
     * Converts from a string value to the represented 
     * enum value.
     * 
     * @param s
     * The string to be converted.
     * 
     * @return
     * The appropriate enum if one can be created from the string, or INVALID
     * if it does not match any valid value.
     */
    public static PartPosition fromString(String s) {
        if (s.equals(FRONT.toString())) {
            return FRONT;
        } else if (s.equals(REAR.toString())) {
            return REAR;
        } else {
            return INVALID;
        }
    }
    
    public int getValue() {
        return this.value;
    }
    
    /**
     * Converts from an integer "location" to an enum.
     * 
     * @param i
     * The position index.
     * 
     * @return
     * The enum representing that position, or INVALID if 
     * no such enum value exists.
     */
    public static PartPosition fromInt(int i) {
        switch(i) {
        case 0:
            return FRONT;
        case 1:
            return REAR;
        default:
            return INVALID;
        }
    }
    

}
