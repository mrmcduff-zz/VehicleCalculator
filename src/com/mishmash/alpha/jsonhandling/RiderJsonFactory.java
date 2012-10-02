package com.mishmash.alpha.jsonhandling;


import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mishmash.alpha.vehicleparts.IVehiclePart;
import com.mishmash.alpha.vehicleparts.Rider;

/**
 * Class used to build Riders out of JSON.
 */
public class RiderJsonFactory {
    private static final List<String> requiredPartNames = Lists.newArrayList(IVehiclePart.NAME_KEY,
            Rider.RIDE_TIME_IN_MINUTES_KEY);
    
    /**
     * Gets a list of Riders from the input JsonArray.
     * 
     * @param jsonArray
     * A JsonArray to be parsed.
     * 
     * @return
     * A list containing all Riders that can be made from the input JsonArray.
     */
    public static List<Rider> getRiders(JsonArray jsonArray) {
        List<Rider> riders = Lists.newArrayList();
        Rider temp = null;
        for (JsonElement element : jsonArray) {
            temp = getSingleRider(element);
            if (temp != null) {
                riders.add(temp);
            }
        }
        return riders;
    }
    
    /**
     * Gets a map of Riders keyed by name from the input JsonArray.
     * 
     * @param jsonArray
     * The JsonArray to be parsed.
     * 
     * @return
     * A map of Riders keyed by their names.
     * 
     */
    public static Map<String, Rider> getRidersMappedByName(JsonArray jsonArray) {
        List<Rider> list = RiderJsonFactory.getRiders(jsonArray);
        Map<String, Rider> map = Maps.newHashMap();
        for (Rider rider : list) {
            map.put(rider.getName(), rider);
        }
        return map;
    }
    
    /**
     * Parses a JsonElement to create a Rider object.
     * 
     * @param element
     * The element to be parsed.
     * 
     * @return
     * A Rider object if one can be made from the JsonElement, or null if not.
     */
    public static Rider getSingleRider(JsonElement element) {
        Rider rider = null;
        JsonObject riderObject = FactoryUtils.getVerifiedJsonObject(element, requiredPartNames);
        if (riderObject != null) {
            try {
                String name = riderObject.get(IVehiclePart.NAME_KEY).getAsString();
                double baseSpeed = riderObject.get(Rider.RIDE_TIME_IN_MINUTES_KEY).getAsDouble();
                rider = new Rider(name, baseSpeed);
            } catch (ClassCastException ccex) {
                ccex.printStackTrace();
            } catch (NumberFormatException nfx) {
                nfx.printStackTrace();
            }
        }
        return rider;
    }
    
}
