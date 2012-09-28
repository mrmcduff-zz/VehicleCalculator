package com.mishmash.alpha.jsonhandling;

import java.util.List;

import com.google.common.collect.Lists;
import com.mishmash.alpha.vehicleparts.IVehicleProperty;
import com.mishmash.alpha.vehicleparts.Rider;

public class RiderJsonFactory {
    private static final List<String> requiredPartNames = Lists.newArrayList(IVehicleProperty.NAME_KEY,
            Rider.RIDE_TIME_IN_MINUTES_KEY);

}
