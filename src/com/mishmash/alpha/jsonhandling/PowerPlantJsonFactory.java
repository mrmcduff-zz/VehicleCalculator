package com.mishmash.alpha.jsonhandling;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mishmash.alpha.VehicleType;
import com.mishmash.alpha.vehicleparts.IVehiclePart;
import com.mishmash.alpha.vehicleparts.PowerPlant;

public class PowerPlantJsonFactory {
    private static final List<String> requiredPartNames = Lists.newArrayList(IVehiclePart.NAME_KEY,
            IVehiclePart.VALID_ON_KEY, PowerPlant.SPEED_IN_MPH_KEY);

    /**
     * Reads in a jsonArray of power plants and returns a List of PowerPlant objects.
     * If any of the items in the jsonArray lacks the required attributes, then no
     * PowerPlant is made for that particular item. Excess attributes are ignored.
     * 
     * @param jsonArray
     * An input array that may or may not contain some power plant items. 
     * 
     * @return
     * A list of all the PowerPlant objects that could be made with the input data.
     */
    public static List<PowerPlant> getPowerPlants(JsonArray jsonArray) {
        List<PowerPlant> powerPlants = new ArrayList<PowerPlant>();
        PowerPlant temp = null;
        for (JsonElement element : jsonArray) {
            temp = getSinglePowerPlant(element);
            if (temp != null) {
                powerPlants.add(temp);
            }
        }
        return powerPlants;
    }
    
    /**
     * Creates a single PowerPlant object out of a JsonElement.
     * 
     * @param element
     * The JsonElement that (in theory) contains the data for the
     * PowerPlant definition.
     *  
     * @return
     * A PowerPlant, or null if no object could be made from the given
     */
    public static PowerPlant getSinglePowerPlant(JsonElement element) {
        PowerPlant plant = null;
        JsonObject plantObject = FactoryUtils.getVerifiedJsonObject(element, requiredPartNames);
        if (plantObject != null) {
            try {
                String name = plantObject.get(IVehiclePart.NAME_KEY).getAsString();
                double baseSpeed = plantObject.get(PowerPlant.SPEED_IN_MPH_KEY).getAsDouble();
                List<VehicleType> validTypes = 
                        FactoryUtils.getValidVehiclesFromJsonObject(
                                plantObject.getAsJsonObject(IVehiclePart.VALID_ON_KEY));
                plant = new PowerPlant(name, baseSpeed, validTypes);
            } catch (ClassCastException ccex) {
                ccex.printStackTrace();
            } catch (NumberFormatException nfx) {
                nfx.printStackTrace();
            }
        }
        return plant;
    }
    
    
}
