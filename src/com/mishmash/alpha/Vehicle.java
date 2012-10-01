/**
 * 
 */
package com.mishmash.alpha;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.mishmash.alpha.vehicleparts.IDistanceModifierPart;
import com.mishmash.alpha.vehicleparts.IVehiclePart;
import com.mishmash.alpha.vehicleparts.PowerPlant;
import com.mishmash.alpha.vehicleparts.Rider;
import com.mishmash.alpha.vehicleparts.VehicleFrame;
import com.mishmash.alpha.vehicleparts.Wheel;

/**
 * @author mrmcduff
 *
 */
public class Vehicle {
    
    private VehicleFrame frame;
    private Rider rider;
    private PowerPlant powerPlant;
    private VehicleType type;
    private boolean hasBeenValidated = false;
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
    
    public boolean isValid() {
        boolean answer = true;
        if (hasBeenValidated) {
            answer = hasBeenValidated;
        } else {
            answer = (rider != null);
            if (answer) {
                answer = answer && hasValidPartsForType();
            }
            
            if (answer) {
                answer = answer && modifierPropertiesValid();
            }
            
            
            // mark the flag
            hasBeenValidated = true;
        }
        
        return answer;
    }
    
    public double getDistance() {
        double distance = 0.0;
        if (this.isValid()) {
            double time = rider.getRideTimeInMinutes() * MINUTES_TO_HOURS_CONVERTER;
            double speed = powerPlant.getSpeedInMph();
            double speedModifier = 1.0;
            double timeModifier = 1.0;
            for (IDistanceModifierPart idmp : this.distanceModifiers) {
                // Stacking modifiers multiplicatively
                speedModifier *= idmp.getSpeedModifierFactor();
                timeModifier *= idmp.getTimeModifierFactor();
            }
            
            time *= timeModifier;
            speed *= speedModifier;
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
        this.distanceModifiers.add(this.frame);
        for (Wheel wheel : wheels) {
            this.distanceModifiers.add(wheel);
        }
    }
    
    private boolean modifierPropertiesValid() {
        boolean answer = true;
        for(IDistanceModifierPart idmp : distanceModifiers) {
            if (!answer) {
                break;
            }
            answer = answer && idmp != null && idmp.hasAllValidModifiers();
        }
        return answer;
    }
    
   
    

}
