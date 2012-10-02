package com.mishmash.alpha.jsonhandling;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mishmash.alpha.VehicleType;

/**
 * A set of static utility functions useful to the JsonFactory classes.
 */
public class FactoryUtils {

    /**
     * Checks to see if a JsonElement is a properly formatted JsonObject, where the formatting 
     * is defined by the given part names. This function does not guard against the Element having
     * more attributes; it only checks that the required attributes are a subset of those actually
     * found on the Element.
     * 
     * @param element
     * The element to inspect.
     * 
     * @param requiredPartNames
     * The required attributes of the object.
     * 
     * @return
     * A JsonObject with all of the same attributes as the input JsonElement IF all required 
     * attributes are present. Null otherwise.
     */
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
    
    /**
     * Gets the valid vehicle types from a valid JsonObject. This only works
     * for JsonObjects where the vehicle types are not modified (such as by
     * appending "Front" or "Rear" to them).
     * 
     * @param validOnObject
     * The JsonObject on which we're looking for vehicle type validators.
     * 
     * @return
     * A list of VehicleTypes that are valid for the input object.
     */
    public static List<VehicleType> getValidVehiclesFromJsonObject(JsonObject validOnObject) {
        List<VehicleType> validVehicleList = Lists.newArrayList();
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
