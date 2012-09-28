package com.mishmash.alpha.jsonhandling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mishmash.alpha.VehicleType;
import com.mishmash.alpha.vehicleparts.IDistanceModifierProperty;
import com.mishmash.alpha.vehicleparts.IVehicleProperty;
import com.mishmash.alpha.vehicleparts.Wheel;

public class WheelJsonFactory {
    
    private static final List<String> requiredPartNames = Lists.newArrayList(IVehicleProperty.NAME_KEY,
            IVehicleProperty.VALID_ON_KEY, IDistanceModifierProperty.SPEED_MODIFIER_KEY,
            IDistanceModifierProperty.TIME_MODIFIER_KEY);
    
    public static List<Wheel> getWheels(JsonArray jsonArray) {
        List<Wheel> wheelList = new ArrayList<Wheel>();
        Wheel temp = null;
        for (JsonElement jsonElement : jsonArray) {
            temp = WheelJsonFactory.getSingleWheel(jsonElement);
            if (temp != null) {
                wheelList.add(temp);
            }
        }
        return wheelList;
    }
    
    @SuppressWarnings("unchecked")
    public static Wheel getSingleWheel(JsonElement jsonElement) {
        Wheel wheel = null;
        JsonObject wheelObject = FactoryUtils.getVerifiedJsonObject(jsonElement, requiredPartNames);
        if (wheelObject != null) {
            try {
                String name = wheelObject.get(IVehicleProperty.NAME_KEY).getAsString();
                double speedModifierPercentage = 
                        wheelObject.get(IDistanceModifierProperty.SPEED_MODIFIER_KEY).getAsDouble();
                double timeModifierPercentage = 
                        wheelObject.get(IDistanceModifierProperty.TIME_MODIFIER_KEY).getAsDouble();
                JsonObject validOnObject = wheelObject.getAsJsonObject(IVehicleProperty.VALID_ON_KEY);
                List<VehicleType> frontWheelTypes = getValidVehiclesForWheel(validOnObject, Wheel.FRONT);
                List<VehicleType> rearWheelTypes = getValidVehiclesForWheel(validOnObject, Wheel.REAR);
                wheel = new Wheel(name, timeModifierPercentage, 
                        speedModifierPercentage, frontWheelTypes, rearWheelTypes);
            } catch (ClassCastException ccex) {
                ccex.printStackTrace();
            }
        }
        return wheel;
    }
    
    
    protected static List<VehicleType> getValidVehiclesForWheel(JsonObject validOnObject, String positionSuffix) {
        List<VehicleType> validVehicleList = new ArrayList<VehicleType>();
        try {
            for (Map.Entry<String,JsonElement> entry : validOnObject.entrySet()) {
                if (entry.getValue().getAsBoolean() && entry.getKey().endsWith(positionSuffix)) {
                    String keyString = entry.getKey();
                    VehicleType type = VehicleType.fromString( 
                            keyString.substring(0, keyString.indexOf(positionSuffix)) );
                    if (type != VehicleType.INVALID) {
                        validVehicleList.add(type);
                    }
                }
            }
        } catch (ClassCastException ccex) {
            ccex.printStackTrace();
        }
        return validVehicleList;
    }
    
    

}
