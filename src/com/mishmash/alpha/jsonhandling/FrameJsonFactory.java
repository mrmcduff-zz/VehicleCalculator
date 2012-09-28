package com.mishmash.alpha.jsonhandling;

import java.util.List;

import com.google.common.collect.Lists;
import com.mishmash.alpha.vehicleparts.IDistanceModifierProperty;
import com.mishmash.alpha.vehicleparts.IVehicleProperty;

public class FrameJsonFactory {
    private static final List<String> requiredPartNames = Lists.newArrayList(IVehicleProperty.NAME_KEY,
            IVehicleProperty.VALID_ON_KEY, IDistanceModifierProperty.SPEED_MODIFIER_KEY,
            IDistanceModifierProperty.TIME_MODIFIER_KEY);

}
