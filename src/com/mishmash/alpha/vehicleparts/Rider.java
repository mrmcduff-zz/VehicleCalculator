package com.mishmash.alpha.vehicleparts;

/**
 * Class representing a rider object. Holds data, not much else.
 */
public class Rider implements IVehiclePart {

    private String name;
    public static final String PROPERTY_NAME = "Riders";
    private double rideTimeInMinutes;
    public static final String RIDE_TIME_IN_MINUTES_KEY = "RideTimeInMinutes";
    private UniversalValidator validator = new UniversalValidator();
    
    public Rider(String name, double rideTimeInMinutes) {
        this.name = name;
        this.rideTimeInMinutes = rideTimeInMinutes;
    }
    
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPropertyName() {
        return Rider.PROPERTY_NAME;
    }
    
    public double getRideTimeInMinutes() {
        return this.rideTimeInMinutes;
    }
    
    @Override
    public IVehicleTypeValidator getValidator() {
        return this.validator;
    }
    
    @Override
    public boolean hasValidAttributes() {
        return (name != null && !name.equals("") && rideTimeInMinutes >= 0);
    }
    
    /**
     * A rider only has two distinguishing characteristics, so they
     * are the same if their names and ride times are equal.
     */
    @Override
    public final boolean equals(Object other) {
        boolean answer = true;
        if ( other != null && other instanceof Rider ) {
            Rider otherRider = (Rider) other;
            if (otherRider.getName() == null) {
                answer = answer && this.name == null;
            } else {
                answer = answer && otherRider.getName().equals(this.name);
            }
            
            answer = answer && PartUtils.doubleEquals(
                    otherRider.getRideTimeInMinutes(), 
                    this.rideTimeInMinutes);
        } else {
            answer = false;
        }
        
        return answer;
    }
    
    /**
     * Using both the built-in hashcode for the name string and a 
     * calculation based on the rideTimeInMinutes. Multiplying by the property name's
     * hashcode to differentiate "Fred, 0.3" from "Fred, 0.4". It's not against the rules
     * for these to have the same hashcode, but it would be preferable not to.
     * 
     * All riders will have the same property name, so if rideTimeInMinutes is zero
     * we aren't losing something that would differentiate them.
     */
    @Override
    public final int hashCode() {
        int hash = 0;
        if (this.name != null) {
            hash += this.name.hashCode();
        }
        hash += Rider.PROPERTY_NAME.hashCode() * PartUtils.getHashableValueFromDouble(rideTimeInMinutes);
        return hash;
    }


}
