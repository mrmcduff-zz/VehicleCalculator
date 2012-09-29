package com.mishmash.alpha.vehicleparts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mishmash.alpha.VehicleType;

public class Wheel implements IVehiclePart, IDistanceModifierPart {

    private String name;
    public static final String PROPERTY_NAME = "Wheels";
    private double timeModifierValue;
    private double speedModifierValue;
    private List<Map<VehicleType, Boolean>> tirePositionMapList = new ArrayList<Map<VehicleType, Boolean> >();
    
    public Wheel(String name, double timeModifierPercentage, double speedModifierPercentage, List<VehicleType>... orderedValidPositions) {
        this.name = name;
        this.timeModifierValue = PartUtils.convertFromPercentageToModifier(timeModifierPercentage);
        this.speedModifierValue = PartUtils.convertFromPercentageToModifier(speedModifierPercentage);
        
        for (List<VehicleType> list : orderedValidPositions) {
            Map<VehicleType, Boolean> positionMap = new HashMap<VehicleType, Boolean>();
            for (VehicleType type : list) {
                positionMap.put(type, true);
            }
            this.tirePositionMapList.add(positionMap);
        }
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getPropertyName() {
        return Wheel.PROPERTY_NAME;
    }
    
    @Override
    public double getTimeModifierFactor() {
        return this.timeModifierValue;
    }
    
    @Override
    public double getSpeedModifierFactor() {
        return this.speedModifierValue;
    }
    
    public boolean isValidFor(VehicleType type, int position) {
        boolean isValid = false;
        if (this.tirePositionMapList.size() > position) {
            Map<VehicleType, Boolean> positionMap = tirePositionMapList.get(position);
            isValid = positionMap.containsKey(type) && positionMap.get(type);
        }
        return isValid;
    }
    
    public int getNumberOfValidPositions() {
        return this.tirePositionMapList.size();
    }
    
    @Override
    public boolean hasAllValidModifiers() {
        return this.timeModifierValue > 0 && this.speedModifierValue > 0;
    }
    
    @Override
    public boolean equals(Object other) {
        boolean answer = true;
        if (other != null && other instanceof Wheel) {
            Wheel otherWheel = (Wheel) other;
            answer = answer && otherWheel.getName().equals(this.getName());
            answer = answer && 
                    PartUtils.doubleEquals(otherWheel.getSpeedModifierFactor(), 
                            this.getSpeedModifierFactor());
            answer = answer && 
                    PartUtils.doubleEquals(otherWheel.getTimeModifierFactor(), 
                            this.getTimeModifierFactor());
            answer = answer && otherWheel.getNumberOfValidPositions() 
                    == this.getNumberOfValidPositions();
            if (answer) {
                for (int i = 0; i < this.tirePositionMapList.size(); ++i) {
                    for (VehicleType type : VehicleType.values()) {
                        answer = answer && (this.isValidFor(type, i) ==
                                otherWheel.isValidFor(type, i));
                        if (!answer) {
                            break;
                        }
                    }
                    if (!answer) {
                        break;
                    }
                }
            }
        } else {
            answer = false;
        }
        return answer;
    }
    
    @Override
    public int hashCode() {
        int hash = this.getName().hashCode();
        hash += (int) (IDistanceModifierPart.SPEED_MODIFIER_KEY.hashCode() *
                this.getSpeedModifierFactor());
        hash += (int) (IDistanceModifierPart.TIME_MODIFIER_KEY.hashCode() *
                this.getTimeModifierFactor());
        for (int i = 0; i < this.tirePositionMapList.size(); ++i) {
            for (VehicleType type : VehicleType.values()) {
                if (this.isValidFor(type, i)) {
                    hash += type.hashCode();
                }
            }
        }
        
        return hash;
    }



}
    

