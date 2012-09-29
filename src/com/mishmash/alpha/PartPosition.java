package com.mishmash.alpha;

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
