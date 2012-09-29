/**
 * 
 */
package com.mishmash.alpha.vehicleparts;


/**
 * @author mrmcduff
 *
 */
public interface IVehiclePart {
    public static final String NAME_KEY = "Name";
    public static final String VALID_ON_KEY = "ValidOn";
    // Something small enough that we consider two doubles who differ by this much to be equal
    public static final double DELTA = 0.0000001;
    
    public String getName();
    public String getPropertyName();
    public IVehicleTypeValidator getValidator();

}
