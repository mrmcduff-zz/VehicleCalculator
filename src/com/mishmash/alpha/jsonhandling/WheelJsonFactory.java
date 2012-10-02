package com.mishmash.alpha.jsonhandling;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mishmash.alpha.PartPosition;
import com.mishmash.alpha.VehicleType;
import com.mishmash.alpha.vehicleparts.IDistanceModifierPart;
import com.mishmash.alpha.vehicleparts.IVehiclePart;
import com.mishmash.alpha.vehicleparts.Wheel;

/**
 * Class to build wheels out of JSON
 */
public class WheelJsonFactory {
    
    private static final List<String> requiredPartNames = Lists.newArrayList(IVehiclePart.NAME_KEY,
            IVehiclePart.VALID_ON_KEY, IDistanceModifierPart.SPEED_MODIFIER_KEY,
            IDistanceModifierPart.TIME_MODIFIER_KEY);
    
    /**
     * Gets a list of wheels from an input JsonArray.
     * 
     * @param jsonArray
     * The JsonArray to parse.
     * 
     * @return
     * All wheels that can be created from the input data.
     */
    public static List<Wheel> getWheels(JsonArray jsonArray) {
        List<Wheel> wheelList = Lists.newArrayList();
        Wheel temp = null;
        for (JsonElement jsonElement : jsonArray) {
            temp = WheelJsonFactory.getSingleWheel(jsonElement);
            if (temp != null) {
                wheelList.add(temp);
            }
        }
        return wheelList;
    }
    
    /**
     * Gets a mapped set of wheels from the input data, where the map key
     * is the name of the wheel.
     * 
     * @param jsonArray
     * The JsonArray to parse.
     * 
     * @return
     * A map of Wheels keyed by their names.
     */
    public static Map<String, Wheel> getWheelsMappedByName(JsonArray jsonArray) {
        List<Wheel> list = WheelJsonFactory.getWheels(jsonArray);
        Map<String, Wheel> map = Maps.newHashMap();
        for (Wheel wheel : list) {
            map.put(wheel.getName(), wheel);
        }
        return map;
    }
    
    /**
     * Creates a single Wheel object from a properly formatted JsonElement.
     * 
     * @param jsonElement
     * The input element to be parsed.
     * 
     * @return
     * A Wheel created using the data in the jsonElement if possible, or null
     * if the jsonElement lacks the required attributes.
     */
    @SuppressWarnings("unchecked")
    public static Wheel getSingleWheel(JsonElement jsonElement) {
        Wheel wheel = null;
        JsonObject wheelObject = FactoryUtils.getVerifiedJsonObject(jsonElement, requiredPartNames);
        if (wheelObject != null) {
            try {
                String name = wheelObject.get(IVehiclePart.NAME_KEY).getAsString();
                double speedModifierPercentage = 
                        wheelObject.get(IDistanceModifierPart.SPEED_MODIFIER_KEY).getAsDouble();
                double timeModifierPercentage = 
                        wheelObject.get(IDistanceModifierPart.TIME_MODIFIER_KEY).getAsDouble();
                JsonObject validOnObject = wheelObject.getAsJsonObject(IVehiclePart.VALID_ON_KEY);
                List<VehicleType> frontWheelTypes = getValidVehiclesForWheel(validOnObject, PartPosition.FRONT.toString());
                List<VehicleType> rearWheelTypes = getValidVehiclesForWheel(validOnObject, PartPosition.REAR.toString());
                wheel = new Wheel(name, timeModifierPercentage, 
                        speedModifierPercentage, frontWheelTypes, rearWheelTypes);
            } catch (ClassCastException ccex) {
                ccex.printStackTrace();
            } catch (NumberFormatException nfx) {
                // Caught when getAsDouble fails.
                nfx.printStackTrace();
            }
        }
        return wheel;
    }
    
    /**
     * Gets the valid vehicles for the given wheel given the valid-on object (keyed with "ValidOn")
     * and the positionSuffix ("Front", "Rear", "Middle", etc).
     * 
     * @param validOnObject
     * The object containing the vehicle validity data.
     * 
     * @param positionSuffix
     * The position of the wheel.
     * 
     * @return
     * A list containing the types of vehicles for which the wheel is valid in the position given.
     */
    protected static List<VehicleType> getValidVehiclesForWheel(JsonObject validOnObject, String positionSuffix) {
        List<VehicleType> validVehicleList = Lists.newArrayList();
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
