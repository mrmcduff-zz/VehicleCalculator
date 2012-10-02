package com.mishmash.alpha.jsonhandling;


import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mishmash.alpha.VehicleType;
import com.mishmash.alpha.vehicleparts.IDistanceModifierPart;
import com.mishmash.alpha.vehicleparts.IVehiclePart;
import com.mishmash.alpha.vehicleparts.VehicleFrame;

/**
 * A class that parses Frame objects from JSON.
 */
public class FrameJsonFactory {
    private static final List<String> requiredPartNames = Lists.newArrayList(IVehiclePart.NAME_KEY,
            IVehiclePart.VALID_ON_KEY, IDistanceModifierPart.SPEED_MODIFIER_KEY,
            IDistanceModifierPart.TIME_MODIFIER_KEY);
    
    /**
     * Gets a list of VehicleFrames from the input JsonArray. Improperly formatted
     * items in the array are ignored.
     * 
     * @param jsonArray
     * An array of frames defined in JSON. 
     * 
     * @return
     * A list of all VehicleFrame objects that could be parsed from the array.
     */
    public static List<VehicleFrame> getVehicleFrames(JsonArray jsonArray) {
        List<VehicleFrame> frames = Lists.newArrayList();
        VehicleFrame temp = null;
        for (JsonElement element : jsonArray) {
            temp = getSingleVehicleFrame(element);
            if (temp != null) {
                frames.add(temp);
            }
        }
        return frames;
    }
    
    /**
     * Parses the JsonArray to get a name-keyed map useful for display purposes.
     * 
     * @param jsonArray
     * The jsonArray to parse.
     * 
     * @return
     * A group of VehicleFrames created from the data and stored according to name.
     */
    public static Map<String, VehicleFrame> getVehicleFramesMappedByName(JsonArray jsonArray) {
        List<VehicleFrame> list = FrameJsonFactory.getVehicleFrames(jsonArray);
        Map<String, VehicleFrame> map = Maps.newHashMap();
        for (VehicleFrame frame : list) {
            map.put(frame.getName(), frame);
        }
        return map;
    }
    
    /**
     * Validates and gets a single VehicleFrame object from the input JsonElement.
     * 
     * @param element
     * The input element.
     * 
     * @return
     * A VehicleFrame based on the element's data if it has the required parts, or null
     * if it doesn't.
     */
    public static VehicleFrame getSingleVehicleFrame(JsonElement element) {
        VehicleFrame frame = null;
        JsonObject frameObject = FactoryUtils.getVerifiedJsonObject(element, requiredPartNames);
        if (frameObject != null) {
            try {
                String name = frameObject.get(IVehiclePart.NAME_KEY).getAsString();
                double timeModifierPercentage = frameObject.get(IDistanceModifierPart.TIME_MODIFIER_KEY).getAsDouble();
                double speedModifierPercentage = frameObject.get(IDistanceModifierPart.SPEED_MODIFIER_KEY).getAsDouble();
                List<VehicleType> validTypes = 
                        FactoryUtils.getValidVehiclesFromJsonObject(
                                frameObject.getAsJsonObject(IVehiclePart.VALID_ON_KEY));
                frame = new VehicleFrame(name, timeModifierPercentage, speedModifierPercentage, validTypes);
            } catch (ClassCastException ccex) {
                ccex.printStackTrace();
            } catch (NumberFormatException nfx) {
                nfx.printStackTrace();
            }
        }
        return frame;
    }

}
