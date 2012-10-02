package com.mishmash.alpha;

/**
 * Enum representing a modifier stacking operation. Modifiers can stack 
 * additively (5% + 10% = 15%) or multiplicatively (1.05 * 1.10) = 1.155
 */
public enum ModifierStackingOperation {
    ADD, MULTIPLY;
    
    /**
     * Overriding toString to get better-formatted
     * string interpretations.
     */
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
    
    /**
     * Convenience function to avoid looping over the results of .values().
     * @return
     */
    public static String[] valueStrings() {
        return new String[]{ADD.toString(), MULTIPLY.toString()};
    }
    
    /**
     * A way to convert back to the enum.
     * @param s
     * The string to be converted.
     * 
     * @return
     * The enum value equal to the string or null if there is none.
     */
    public static ModifierStackingOperation fromString(String s) {
        if (s.equals(ADD.toString())) {
            return ADD;
        } else if (s.equals(MULTIPLY.toString())) {
            return MULTIPLY;
        } else {
            return null;
        }
    }

}
