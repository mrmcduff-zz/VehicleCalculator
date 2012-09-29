package com.mishmash.alpha;

public enum PartPosition {
    FRONT, REAR, INVALID;
    
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
    
    public PartPosition fromString(String s) {
        if (s.equals(FRONT.toString())) {
            return FRONT;
        } else if (s.equals(REAR.toString())) {
            return REAR;
        } else {
            return INVALID;
        }
    }
    

}
