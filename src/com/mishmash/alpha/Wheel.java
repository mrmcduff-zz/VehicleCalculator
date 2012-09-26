package com.mishmash.alpha;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wheel implements IVehicleProperty{

    private String name;
    private static final String PROPERTY_NAME = "Wheel";
    private double timeModifierPercentage;
    private double speedModifierPercentage;
    private List<Map<VehicleType, Boolean>> tirePositionMapList = new ArrayList<Map<VehicleType, Boolean> >();
    
    public Wheel(String name, double timeModifier, double speedModifier, List<VehicleType>... orderedValidPositions) {
        this.name = name;
        this.timeModifierPercentage = timeModifier;
        this.speedModifierPercentage = speedModifier;
        
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
    
    public double getTimeModifierPercentage() {
        return this.timeModifierPercentage;
    }
    
    public double getSpeedModifierPercentage() {
        return this.speedModifierPercentage;
    }
    
    public boolean isValidFor(VehicleType type, int position) {
        boolean isValid = false;
        if (this.tirePositionMapList.size() > position) {
            Map<VehicleType, Boolean> positionMap = tirePositionMapList.get(position);
            isValid = positionMap.containsKey(type) && positionMap.get(type);
        }
        return isValid;
    }

}
    

