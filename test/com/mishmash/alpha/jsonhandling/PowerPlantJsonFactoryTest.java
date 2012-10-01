package com.mishmash.alpha.jsonhandling;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mishmash.alpha.VehicleType;
import com.mishmash.alpha.vehicleparts.IVehiclePart;
import com.mishmash.alpha.vehicleparts.PowerPlant;

public class PowerPlantJsonFactoryTest {

    private static String rawData = "";
    private static JsonArray jsonArray = null;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        rawData = IOUtils.toString( 
                RiderJsonFactoryTest.class.getResourceAsStream(TestUtils.LOCAL_SOURCE),
                "UTF-8");
        List<String> categoryList = Lists.newArrayList(PowerPlant.PROPERTY_NAME);
        Map<String, JsonArray> categories = DataParser.seperateCategories(rawData, categoryList);
        jsonArray = categories.get(PowerPlant.PROPERTY_NAME);
    }

    @Test
    public void testGetPowerPlants() {
        assertEquals(3, jsonArray.size());
        List<VehicleType> nonMotored = Lists.newArrayList(VehicleType.BICYCLE, VehicleType.TRICYCLE);
        List<VehicleType> motored = Lists.newArrayList(VehicleType.SCOOTER);
        PowerPlant p1 = new PowerPlant("Pedals", 20, nonMotored);
        PowerPlant p2 = new PowerPlant("50cc Motor", 40, motored);
        PowerPlant p3 = new PowerPlant("100cc Motor", 60, motored);
        List<PowerPlant> expected = Lists.newArrayList(p1, p2, p3);
        List<PowerPlant> actual = PowerPlantJsonFactory.getPowerPlants(jsonArray);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void testGetSinglePowerPlant() {
        JsonObject jsonPlant = new JsonObject();
        jsonPlant.addProperty(IVehiclePart.NAME_KEY, "Test Plant");
        jsonPlant.addProperty(PowerPlant.SPEED_IN_MPH_KEY, 20);
        
        JsonObject validationObject = new JsonObject();
        validationObject.addProperty(VehicleType.BICYCLE.toString(), true);
        
        jsonPlant.add(IVehiclePart.VALID_ON_KEY, validationObject);
        
        PowerPlant expected = new PowerPlant("Test Plant", 20, 
                Lists.newArrayList(VehicleType.BICYCLE));
        assertEquals(expected, PowerPlantJsonFactory.getSinglePowerPlant(jsonPlant));

    }

}
