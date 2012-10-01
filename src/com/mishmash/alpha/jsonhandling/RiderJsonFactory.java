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


public class RiderJsonFactory {
    private static final List<String> requiredPartNames = Lists.newArrayList(IVehiclePart.NAME_KEY,
            Rider.RIDE_TIME_IN_MINUTES_KEY);
    
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
    
    public static Map<String, Rider> getRidersMappedByName(JsonArray jsonArray) {
        List<Rider> list = RiderJsonFactory.getRiders(jsonArray);
        Map<String, Rider> map = Maps.newHashMap();
        for (Rider rider : list) {
            map.put(rider.getName(), rider);
        }
        return map;
    }
    
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
