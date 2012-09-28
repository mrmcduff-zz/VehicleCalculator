package com.mishmash.alpha.jsonhandling;

import java.util.List;

import com.google.common.collect.Lists;
import com.mishmash.alpha.vehicleparts.IVehicleProperty;
import com.mishmash.alpha.vehicleparts.PowerPlant;

public class PowerPlantJsonFactory {
    private static final List<String> requiredPartNames = Lists.newArrayList(IVehicleProperty.NAME_KEY,
            IVehicleProperty.VALID_ON_KEY, PowerPlant.SPEED_IN_MPH_KEY);

}
