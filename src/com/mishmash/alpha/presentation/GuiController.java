package com.mishmash.alpha.presentation;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.mishmash.alpha.Vehicle;
import com.mishmash.alpha.VehicleFactory;
import com.mishmash.alpha.VehicleType;


public class GuiController {

    private VehicleFactory factory = VehicleFactory.getInstance();
    private static final String DATA_SOURCE = "http://www.atlanticbt.com/mobiletest/json.txt";
    private Map<VehicleGuiPart, String> partMap = Maps.newConcurrentMap();
    private VehicleType type = VehicleType.INVALID;
    
    public GuiController() {
        factory.setDataSource(DATA_SOURCE);
    }
    
    public void updateMap(VehicleGuiPart part, String partName) {
        if (part != null) {
            if (partName == null) {
                partMap.remove(part);
            } else {
                partMap.put(part, partName);
            }

        }
    }
    
    public String getOutPutText() {
        String output = "";
        Vehicle vehicle = factory.createVehicleWithData(type, partMap);
        
        if (vehicle != null && vehicle.isValid()) {
            double distance = vehicle.getDistance();
            DecimalFormat df = new DecimalFormat("##.###");
            output = String.format("The %s ridden by %s traveled %s miles.", type.toString(), 
                    partMap.get(VehicleGuiPart.RIDER), df.format(distance));
        } else {
            output = "There is a problem with your vehicle configuration.";
        }
        
        return output;
    }
    
    
    public List<String> getPartsForType(VehicleType type, VehicleGuiPart guiPart) {
        // Cache the type.
        this.type = type;
        List<String> partNames = factory.getPartNamesForType(type, guiPart);
        return partNames;
    }
}
