package com.mishmash.alpha.jsonhandling;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mishmash.alpha.VehicleType;
import com.mishmash.alpha.vehicleparts.IDistanceModifierPart;
import com.mishmash.alpha.vehicleparts.IVehiclePart;
import com.mishmash.alpha.vehicleparts.VehicleFrame;

public class FrameJsonFactory {
    private static final List<String> requiredPartNames = Lists.newArrayList(IVehiclePart.NAME_KEY,
            IVehiclePart.VALID_ON_KEY, IDistanceModifierPart.SPEED_MODIFIER_KEY,
            IDistanceModifierPart.TIME_MODIFIER_KEY);
    
    public static List<VehicleFrame> getVehicleFrames(JsonArray jsonArray) {
        List<VehicleFrame> frames = new ArrayList<VehicleFrame>();
        VehicleFrame temp = null;
        for (JsonElement element : jsonArray) {
            temp = getSingleVehicleFrame(element);
            if (temp != null) {
                frames.add(temp);
            }
        }
        return frames;
    }
    
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
