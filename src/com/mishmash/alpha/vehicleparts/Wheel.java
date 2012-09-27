package com.mishmash.alpha.vehicleparts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mishmash.alpha.VehicleType;

public class Wheel implements IVehicleProperty, IDistanceModifierProperty {

    private String name;
    private static final String PROPERTY_NAME = "Wheels";
    private double timeModifierValue;
    private double speedModifierValue;
    private List<Map<VehicleType, Boolean>> tirePositionMapList = new ArrayList<Map<VehicleType, Boolean> >();
    
    public Wheel(String name, double timeModifierPercentage, double speedModifierPercentage, List<VehicleType>... orderedValidPositions) {
        this.name = name;
        this.timeModifierValue = PropertyUtilities.convertFromPercentageToModifier(timeModifierPercentage);
        this.speedModifierValue = PropertyUtilities.convertFromPercentageToModifier(speedModifierPercentage);
        
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
    
    @Override
    public boolean hasAllValidModifiers() {
        return this.timeModifierValue > 0 && this.speedModifierValue > 0;
    }

}
    

