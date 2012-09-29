/**
 * 
 */
package com.mishmash.alpha;

import java.util.ArrayList;
import java.util.List;

import com.mishmash.alpha.vehicleparts.IDistanceModifierPart;
import com.mishmash.alpha.vehicleparts.UnaryVehicleProperty;
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
    private List<UnaryVehicleProperty> unaryProperties = new ArrayList<UnaryVehicleProperty>();
    private List<IDistanceModifierPart> distanceModifiers = new ArrayList<IDistanceModifierPart>();
    private List<Wheel> wheels = new ArrayList<Wheel>();
    private final int REQUIRED_WHEEL_COUNT = 2;
    private final double MINUTES_TO_HOURS_CONVERTER = 1/60.0;
    
    public Vehicle(VehicleType type, Rider rider, VehicleFrame vehicleFrame, 
            PowerPlant powerPlant, List<Wheel> wheels) {
        this.type = type;
        this.rider = rider;
        this.frame = vehicleFrame;
        this.powerPlant = powerPlant;
        this.wheels = wheels;
        
        sortUnaryProperties();
        sortModifierProperties();
    }
    
    public boolean isValid() {
        boolean answer = true;
        if (hasBeenValidated) {
            answer = hasBeenValidated;
        } else {
            answer = (rider != null);
            if (answer) {
                answer = answer && unaryPropertiesValidForType();
            }
            
            if (answer) {
                answer = answer && modifierPropertiesValid();
            }
            
            if (answer) {
                answer =  answer && wheelsValidForTypeAndLocation();
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
    
    private void sortUnaryProperties() {
        this.unaryProperties.add(this.frame);
        this.unaryProperties.add(this.powerPlant);
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
    
    private boolean unaryPropertiesValidForType() {
        boolean answer = true;
        for(UnaryVehicleProperty uvp : unaryProperties) {
            if (!answer) {
                break;
            }
            answer = answer && (uvp != null) && (uvp.isValidOn(this.type));
        }
        
        return answer;
    }
    
    private boolean wheelsValidForTypeAndLocation() {
        boolean answer = wheels.size() == REQUIRED_WHEEL_COUNT;
        for (int i = 0; i < wheels.size(); ++i) {
            if (!answer) {
                break;
            }
            answer = answer && wheels.get(i) != null && wheels.get(i).isValidFor(this.type, i);
        }
        return answer;
    }
    
    

}
