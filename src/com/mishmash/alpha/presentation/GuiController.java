package com.mishmash.alpha.presentation;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.mishmash.alpha.ModifierOperation;
import com.mishmash.alpha.Vehicle;
import com.mishmash.alpha.VehicleFactory;
import com.mishmash.alpha.VehicleType;

/**
 * Handles GUI interaction with the framework. Does not construct any items, but
 * has an instance of the VehicleFactory that can be used to get all objects.
 *
 */
public class GuiController {

    private VehicleFactory factory = VehicleFactory.getInstance();
    private static final String DATA_SOURCE = "http://www.atlanticbt.com/mobiletest/json.txt";
    private Map<VehicleGuiPart, String> partMap = Maps.newConcurrentMap();
    private VehicleType type = VehicleType.INVALID;
    private ModifierOperation operation = ModifierOperation.ADD;
    
    public GuiController() {
        factory.setDataSource(DATA_SOURCE);
    }
    
    /**
     * Updates the map that connects vehicle parts (as the gui understands them) 
     * to the name of the part being used.
     * 
     * @param part
     * The vehicle part as understood by the gui.
     * 
     * @param partName
     * The name of the part in question.
     */
    public void updateMap(VehicleGuiPart part, String partName) {
        if (part != null) {
            if (partName == null) {
                partMap.remove(part);
            } else {
                partMap.put(part, partName);
            }
        }
    }
    
    /**
     * Gets the output text to display to the user, usually a distance traveled.
     * 
     * @return
     * A string to display to the user about the output of the vehicle created.
     */
    public String getOutPutText() {
        String output = "";
        Vehicle vehicle = factory.createVehicleWithData(type, partMap);
        
        if (vehicle != null && vehicle.isValid()) {
            double distance = vehicle.getDistance(operation);
            DecimalFormat df = new DecimalFormat("##.###");
            output = String.format("The %s ridden by %s traveled %s miles.", type.toString(), 
                    partMap.get(VehicleGuiPart.RIDER), df.format(distance));
        } else {
            output = "There is a problem with your vehicle configuration.";
        }
        
        return output;
    }
    
    /**
     * Gets the list of available parts (designated by name) for the given
     * gui vehicle part and type of vehicle. The vehicle type is cached
     * for future calculations.
     * 
     * @param type
     * The vehicle type.
     * 
     * @param guiPart
     * The vehicle part.
     * 
     * @return
     * A list of allowable values to be used in the display.
     */
    public List<String> getPartsForType(VehicleType type, VehicleGuiPart guiPart) {
        // Cache the type.
        this.type = type;
        List<String> partNames = factory.getPartNamesForType(type, guiPart);
        return partNames;
    }
    
    public ModifierOperation getModiferStackingOperation() {
        return this.operation;
    }
    
    public void setModifierStackingOperation(ModifierOperation op) {
        if (op != null) {
            this.operation = op;
        }
    }
    
}
