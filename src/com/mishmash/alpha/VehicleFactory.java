package com.mishmash.alpha;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.mishmash.alpha.jsonhandling.FrameJsonFactory;
import com.mishmash.alpha.jsonhandling.ItemGatherer;
import com.mishmash.alpha.jsonhandling.PowerPlantJsonFactory;
import com.mishmash.alpha.jsonhandling.RiderJsonFactory;
import com.mishmash.alpha.jsonhandling.WheelJsonFactory;
import com.mishmash.alpha.presentation.VehicleGuiPart;
import com.mishmash.alpha.vehicleparts.PowerPlant;
import com.mishmash.alpha.vehicleparts.Rider;
import com.mishmash.alpha.vehicleparts.VehicleFrame;
import com.mishmash.alpha.vehicleparts.Wheel;

public class VehicleFactory {

    private static VehicleFactory instance;
    private Map<String, Wheel> wheels = Maps.newHashMap();
    private Map<String, Rider> riders = Maps.newHashMap();
    private Map<String, PowerPlant> powerPlants = Maps.newHashMap();
    private Map<String, VehicleFrame> frames = Maps.newHashMap();
    
    private VehicleFactory() {
        
    }
    
    public static VehicleFactory getInstance() {
        if (instance == null) {
            synchronized(VehicleFactory.class) {
                if (instance == null) {
                    instance = new VehicleFactory();
                }
            }
        }
        
        return instance;
    }
    
    public void setDataSource(String source) {
        this.fillDataStoresFromSource(source);
    }
    
    public List<String> getRequiredVehiclePartNames() {
        return Lists.newArrayList(Rider.PROPERTY_NAME, 
                VehicleFrame.PROPERTY_NAME, Wheel.PROPERTY_NAME, 
                PowerPlant.PROPERTY_NAME);
    }
    
    private boolean fillDataStoresFromSource(String source) {
        boolean successfulDataPull = true;
        String rawData = ItemGatherer.getSmallAmountOfTextDataFromSource(source);
        Map<String, JsonArray> categoryMap = ItemGatherer.seperateCategories(rawData,
                this.getRequiredVehiclePartNames());
        if (categoryMap.containsKey(Wheel.PROPERTY_NAME) &&
                categoryMap.containsKey(VehicleFrame.PROPERTY_NAME) &&
                categoryMap.containsKey(Rider.PROPERTY_NAME) &&
                categoryMap.containsKey(PowerPlant.PROPERTY_NAME)){
            
            wheels = WheelJsonFactory.getWheelsMappedByName(categoryMap.get(Wheel.PROPERTY_NAME));
            frames = FrameJsonFactory.getVehicleFramesMappedByName(categoryMap.get(VehicleFrame.PROPERTY_NAME));
            riders = RiderJsonFactory.getRidersMappedByName(categoryMap.get(Rider.PROPERTY_NAME));
            powerPlants = PowerPlantJsonFactory.getPowerPlantsMappedByName(categoryMap.get(PowerPlant.PROPERTY_NAME));
            
        } else {
            successfulDataPull = false;
        }
        
        return successfulDataPull;
    }
    
    public List<String> getPartNamesForType(VehicleType type, VehicleGuiPart guiPart) {
        List<String> partNames = Lists.newArrayList();
        switch(guiPart) {
        case FRAME:
            for (VehicleFrame frame : frames.values()) {
                if (frame.getValidator().isValidForType(type)) {
                    partNames.add(frame.getName());
                }
            }
            break;
        case FRONT_WHEEL:
            for (Wheel wheel : wheels.values()) {
                if (wheel.getValidator().isValidForTypeWithParameters(type, 
                        PartPosition.FRONT.toString())) {
                    partNames.add(wheel.getName());
                }
            }
            break;
        case REAR_WHEEL:
            for (Wheel wheel : wheels.values()) {
                if (wheel.getValidator().isValidForTypeWithParameters(type, 
                        PartPosition.REAR.toString())) {
                    partNames.add(wheel.getName());
                }
            }
            break;
        case POWER_PLANT:
            for (PowerPlant plant : powerPlants.values()) {
                if (plant.getValidator().isValidForType(type)) {
                    partNames.add(plant.getName());
                }
            }
            break;
        case RIDER:
            for (Rider rider : riders.values()) {
                if (rider.getValidator().isValidForType(type)) {
                    partNames.add(rider.getName());
                }
            }
            break;
        default:
            System.err.println("Attempting to get part names for a part the factory doesn't recognize.");
        }
        return partNames;
    }
    
    public Vehicle createVehicleWithData(VehicleType type, Map<VehicleGuiPart, String> userData) {
        Vehicle vehicle = null;
        VehicleFrame frame = frames.get(userData.get(VehicleGuiPart.FRAME));
        Wheel frontWheel = wheels.get(userData.get(VehicleGuiPart.FRONT_WHEEL));
        Wheel rearWheel = wheels.get(userData.get(VehicleGuiPart.REAR_WHEEL));
        PowerPlant powerPlant = powerPlants.get(userData.get(VehicleGuiPart.POWER_PLANT));
        Rider rider = riders.get(userData.get(VehicleGuiPart.RIDER));
        if (frame != null && frontWheel != null && 
                rearWheel != null && powerPlant != null && 
                rider != null && type != VehicleType.INVALID) {
            vehicle = new Vehicle(type, rider, frame, 
                    powerPlant, Lists.newArrayList(frontWheel, rearWheel));
        }
        return vehicle;
    }
    

}
