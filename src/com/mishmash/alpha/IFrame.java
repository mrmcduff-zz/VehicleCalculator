/**
 * 
 */
package com.mishmash.alpha;

/**
 * @author mrmcduff
 *
 */
public interface IFrame extends IVehicleProperty {
    public double getTimeModifier();
    public double getSpeedModifier();
    public boolean isValidOn(VehicleType type);
}
