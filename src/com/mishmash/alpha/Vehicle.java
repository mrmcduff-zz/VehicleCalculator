/**
 * 
 */
package com.mishmash.alpha;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.mishmash.alpha.vehicleparts.IDistanceModifierPart;
import com.mishmash.alpha.vehicleparts.IVehiclePart;
import com.mishmash.alpha.vehicleparts.PartUtils;
import com.mishmash.alpha.vehicleparts.PowerPlant;
import com.mishmash.alpha.vehicleparts.Rider;
import com.mishmash.alpha.vehicleparts.VehicleFrame;
import com.mishmash.alpha.vehicleparts.Wheel;

/**
 * Reperesents the star of this application, the Vehicle. Vehicles have limited functionality
 * other than to check their own validity (in case they've been given invalid parts) and to
 * calculate the distance that they could travel. Vehicles deliberately don't have
 * setter methods for the parts. If you want to change the parts of a vehicle, create a new
 * vehicle.
 *
 */
public class Vehicle {
    
    private VehicleFrame frame;
    private Rider rider;
    private PowerPlant powerPlant;
    private VehicleType type;
    private boolean hasBeenValidated = false;
    private boolean cachedValidation = false;
    private List<IVehiclePart> parts = Lists.newArrayList();
    private List<IDistanceModifierPart> distanceModifiers = Lists.newArrayList();
    private List<Wheel> wheels = new ArrayList<Wheel>();
    private final double MINUTES_TO_HOURS_CONVERTER = 1/60.0;
    
    /**
     * Creates a new vehicle based on the input data.
     * 
     * @param type
     * The type of vehicle.
     * 
     * @param rider
     * The vehicle's rider.
     * 
     * @param vehicleFrame
     * The vehicle's frame.
     * 
     * @param powerPlant
     * The vehicle's power plant.
     * 
     * @param wheels
     * An list of the vehicle's wheels, ordered front-to-back. 
     */
    public Vehicle(VehicleType type, Rider rider, VehicleFrame vehicleFrame, 
            PowerPlant powerPlant, List<Wheel> wheels) {
        this.type = type;
        this.rider = rider;
        this.frame = vehicleFrame;
        this.powerPlant = powerPlant;
        this.wheels = wheels;
        
        parts.add(rider);
        parts.add(vehicleFrame);
        parts.add(powerPlant);
        parts.addAll(wheels);

        sortModifierProperties();
    }
    
    /**
     * A lazy validator. Because the Vehicle can't be altered 
     * after construction, we only need to check this once.
     * 
     * @return
     * True if all parts are valid for the type of the vehicle and 
     * all modifiers have valid values.
     */
    public boolean isValid() {
        boolean answer = true;
        if (hasBeenValidated) {
            answer = cachedValidation;
        } else {
            answer = (rider != null);
            
            for (IVehiclePart part : parts) {
                answer = answer && part.hasValidAttributes();
            }
            
            if (answer) {
                answer = answer && hasValidPartsForType();
            }
            
            // mark the flag
            hasBeenValidated = true;
            cachedValidation = answer;
        }
        
        return answer;
    }
    
    /**
     * Calculates the distance a vehicle can travel, using whichever 
     * stacking operation it is fed.
     * 
     * @param op
     * How to stack the modifiers (additively or multiplicatively)
     * 
     * @return
     * The distance traveled.
     */
    public double getDistance(ModifierStackingOperation op) {
        
        double distance = 0.0;
        if (this.isValid()) {
            double time = rider.getRideTimeInMinutes() * MINUTES_TO_HOURS_CONVERTER;
            double speed = powerPlant.getSpeedInMph();
            
            /*
             * Initialized modifiers multiply the speed and time by 1.0
             */
            double speedModifier = 1.0;
            double timeModifier = 1.0;

            if (op == ModifierStackingOperation.MULTIPLY) {
                for (IDistanceModifierPart idmp : this.distanceModifiers) {
                    // Stacking modifiers multiplicatively
                    speedModifier *= PartUtils.convertFromPercentageToMultiplicativeModifier(
                            idmp.getSpeedModifierPercentage());
                    timeModifier *= PartUtils.convertFromPercentageToMultiplicativeModifier(
                            idmp.getTimeModifierPercentage());
                }
                
            } else {
                for (IDistanceModifierPart idmp : this.distanceModifiers) {
                    speedModifier += PartUtils.convertFromPercentageToAdditiveModifier(
                            idmp.getSpeedModifierPercentage());
                    
                    timeModifier += PartUtils.convertFromPercentageToAdditiveModifier(
                            idmp.getTimeModifierPercentage());
                }
            }
            
            /*
             * It is impossible to ride the vehicle for negative time
             * or at negative speed (speed is not a vector-based quantity),
             * so if our modifier stacking operation allows the total
             * modifier to go negative, we simply set the modified value to zero.
             */
            if (timeModifier >= 0) {   
                time *= timeModifier;
            } else {
                time = 0;
            }
            
            if (speedModifier >= 0) {
                speed*= speedModifier;
            } else {
                speed = 0;
            }

            distance = speed * time;
            
        } 
        return distance;
    }
    
    private boolean hasValidPartsForType() {
        boolean isValid = true;
        for (IVehiclePart part : parts) {
            if (!(part instanceof Wheel))  {
                isValid = isValid && 
                        (part.getValidator() != null) &&
                        part.getValidator().isValidForType(this.type);
                
                if (!isValid) {
                    break;
                }
            }
        }
        
        if (isValid) {
            for (int i = 0; i < wheels.size(); ++i) {
                isValid = isValid && wheels.get(i).getValidator() != null &&
                        wheels.get(i).getValidator().isValidForTypeWithParameters(
                                this.type, PartPosition.fromInt(i).toString());
                
                if (!isValid) {
                    break;
                }
            }
        }
        
        return isValid;
    }
    
    private void sortModifierProperties() {
        distanceModifiers.add(this.frame);
        for (Wheel wheel : wheels) {
            distanceModifiers.add(wheel);
        }
    }
    
    
   
    

}
