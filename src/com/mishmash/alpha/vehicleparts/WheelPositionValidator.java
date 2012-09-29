package com.mishmash.alpha.vehicleparts;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.mishmash.alpha.PartPosition;
import com.mishmash.alpha.VehicleType;

public class WheelPositionValidator implements IVehicleTypeValidator {

    private Map<PartPosition, Map<VehicleType, Boolean>> positionKeyedMapOfMaps = Maps.newHashMap();
    
    /**
     * Empty constructor. Set the properties using setValidTypesForPosition.
     */
    public WheelPositionValidator() {}
    
    /**
     * Sets the given list to all be valid vehicle types for the given position.
     * 
     * @param position
     * The position for which the list of types is valid.
     * 
     * @param validVehicles
     * The list of types valid at the given position.
     */
    public void setValidTypesForPosition(PartPosition position, List<VehicleType> validVehicles) {
        Map<VehicleType, Boolean> positionMap = Maps.newHashMap();
        for (VehicleType type : validVehicles) {
            positionMap.put(type, true);
        }
        positionKeyedMapOfMaps.put(position, positionMap);
    }
    
    @Override
    public boolean isValidForType(VehicleType type) {
        boolean isValid = true;
        for (PartPosition position : PartPosition.values()) {
            if (position != PartPosition.INVALID) {
                isValid = isValid && this.isValidForTypeWithParameters(type, position.toString());
            }
            
            if (!isValid) {
                break;
            }
        }
        return isValid;
    }

    @Override
    public boolean isValidForTypeWithParameters(VehicleType type,
            String... args) {
        boolean isValid = false;
        PartPosition position = PartPosition.INVALID;
        if(args.length > 0) {
            position = PartPosition.fromString(args[0]);
        }
        
        // We have to have a map for this position,
        // that map has to have an entry for the type,
        // and finally that mapped value must be true.
        isValid = positionKeyedMapOfMaps.containsKey(position) &&
                positionKeyedMapOfMaps.get(position).containsKey(type) &&
                positionKeyedMapOfMaps.get(position).get(type);
        
        return isValid;
    }
    
    public int getNumberOfValidPositions() {
        return positionKeyedMapOfMaps.size();
    }
   
    
    @Override
    public final boolean equals(Object other) {
        boolean equals = true;
        if (other != null && other instanceof WheelPositionValidator) {
            WheelPositionValidator otherWpv = (WheelPositionValidator) other;
            equals = equals && otherWpv.getNumberOfValidPositions() == this.getNumberOfValidPositions();
            // No need to do the loops if we're already off.
            if (equals) {
                for (PartPosition position : PartPosition.values()) {
                    if (position != PartPosition.INVALID) {
                        for (VehicleType type : VehicleType.values()) {
                            if (type != VehicleType.INVALID) {
                                equals = 
                                    (otherWpv.isValidForTypeWithParameters(type, 
                                        position.toString()) == 
                                        this.isValidForTypeWithParameters(type, 
                                            position.toString()));
                            }
                            
                            if (!equals) {
                                break;
                            }
                        } // Close of the loop over VehicleType
                    }
                    
                    if (!equals) {
                        break;
                    }
                }// close of the loop over PartPositions.
            }
        } else {
            equals = false;
        }
        return equals;
    }
    
    @Override
    public int hashCode() {
        return 1;
    }


}
