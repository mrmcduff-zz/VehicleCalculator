package com.mishmash.alpha.vehicleparts;

public class Rider implements IVehicleProperty {

    private String name;
    private static final String PROPERTY_NAME = "Riders";
    private double rideTimeInMinutes;
    
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

}
