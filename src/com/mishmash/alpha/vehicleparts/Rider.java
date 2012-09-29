package com.mishmash.alpha.vehicleparts;


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
    public final boolean equals(Object other) {
        if ( other != null && other instanceof Rider ) {
            Rider otherRider = (Rider) other;
            return otherRider.getName().equals(this.name) &&
                    PartUtils.doubleEquals(
                            otherRider.getRideTimeInMinutes(), 
                            this.rideTimeInMinutes); 
        } else {
            return false;
        }
    }
    
    /**
     * Using both the built-in hashcode for the name string and a 
     * calculation based on the rideTimeInMinutes. Multiplying by the property name's
     * hashcode to differentiate "Fred, 0.3" from "Fred, 0.4". It's not against the rules
     * for these to have the same hashcode, but it would be preferable not to.
     * 
     * All riders will have the same propery name, so if rideTimeInMinutes is zero
     * we aren't losing something that would differentiate them.
     */
    @Override
    public final int hashCode() {
        return this.name.hashCode() + 
                (int) (Rider.PROPERTY_NAME.hashCode() * this.rideTimeInMinutes);
    }


}
