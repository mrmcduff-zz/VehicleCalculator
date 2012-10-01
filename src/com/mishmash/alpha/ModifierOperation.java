package com.mishmash.alpha;

public enum ModifierOperation {
    ADD, MULTIPLY;
    
    @Override
    public String toString() {
        switch(this) {
        case ADD:
            return "Addition";
        case MULTIPLY:
            return "Multiplication";
        default:
            return "Unknown Operation";
        }
    }
    
    public static String[] valueStrings() {
        return new String[]{ADD.toString(), MULTIPLY.toString()};
    }
    
    public static ModifierOperation fromString(String s) {
        if (s.equals(ADD.toString())) {
            return ADD;
        } else if (s.equals(MULTIPLY.toString())) {
            return MULTIPLY;
        } else {
            return null;
        }
    }

}
