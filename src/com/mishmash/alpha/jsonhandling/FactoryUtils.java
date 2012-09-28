package com.mishmash.alpha.jsonhandling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mishmash.alpha.VehicleType;

public class FactoryUtils {

    public static JsonObject getVerifiedJsonObject(JsonElement element, List<String> requiredPartNames) {
        JsonObject verifiedObject = null;
        if (element != null && element.isJsonObject()) {
            JsonObject jobj = element.getAsJsonObject();
            boolean hasParts = true;
            for (String s : requiredPartNames) { 
                hasParts = hasParts && jobj.has(s);
            }
            
            verifiedObject = hasParts ? jobj : null;
        }
        return verifiedObject;
    }
    
    public static List<VehicleType> getValidVehiclesFromJsonObject(JsonObject validOnObject) {
        List<VehicleType> validVehicleList = new ArrayList<VehicleType>();
        try {
            for (Map.Entry<String,JsonElement> entry : validOnObject.entrySet()) {
                if (entry.getValue().getAsBoolean()) {
                    VehicleType type = VehicleType.fromString(entry.getKey());
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
