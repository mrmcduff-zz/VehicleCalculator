/**
 * 
 */
package com.mishmash.alpha;

import com.mishmash.alpha.vehicleparts.IVehicleProperty;

/**
 * @author mrmcduff
 *
 */
public interface IFrame extends IVehicleProperty {
    public double getTimeModifier();
    public double getSpeedModifier();
    public boolean isValidOn(VehicleType type);
}
