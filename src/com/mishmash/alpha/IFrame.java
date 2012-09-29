/**
 * 
 */
package com.mishmash.alpha;

import com.mishmash.alpha.vehicleparts.IVehiclePart;

/**
 * @author mrmcduff
 *
 */
public interface IFrame extends IVehiclePart {
    public double getTimeModifier();
    public double getSpeedModifier();
    public boolean isValidOn(VehicleType type);
}
