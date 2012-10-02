/**
 * 
 */
package com.mishmash.alpha.vehicleparts;


/**
 * The interface for all vehicle parts (which includes riders)
 *
 */
public interface IVehiclePart {
    public static final String NAME_KEY = "Name";
    public static final String VALID_ON_KEY = "ValidOn";
    // Something small enough that we consider two doubles who differ by this much to be equal
    // so far as our vehicle parts are concerned.
    public static final double DELTA = 0.0000001;
    
    public String getName();
    public String getPropertyName();
    public IVehicleTypeValidator getValidator();
    public boolean hasValidAttributes();

}
