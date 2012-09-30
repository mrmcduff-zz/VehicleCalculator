package com.mishmash.alpha.jsonhandling;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;


import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mishmash.alpha.PartPosition;
import com.mishmash.alpha.VehicleType;
import com.mishmash.alpha.vehicleparts.IDistanceModifierPart;
import com.mishmash.alpha.vehicleparts.IVehiclePart;
import com.mishmash.alpha.vehicleparts.Wheel;

public class WheelJsonFactoryTest {

    private static String rawData = "";
    private static JsonArray jsonArray = null;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        rawData = IOUtils.toString( 
                RiderJsonFactoryTest.class.getResourceAsStream(TestUtils.LOCAL_SOURCE),
                "UTF-8");
        List<String> categoryList = Lists.newArrayList(Wheel.PROPERTY_NAME);
        Map<String, JsonArray> categories = DataParser.seperateCategories(rawData, categoryList);
        jsonArray = categories.get(Wheel.PROPERTY_NAME);
    }

    @Test
    public void testGetWheels() {
        assertEquals(5, jsonArray.size());
        List<Wheel> wheels = WheelJsonFactory.getWheels(jsonArray);
        assertEquals(jsonArray.size(), wheels.size());
        
        JsonArray modifiedArray = new JsonArray();
        for (JsonElement element : jsonArray) {
            modifiedArray.add(element);
            modifiedArray.add(new JsonObject());
        } 
        modifiedArray.add(new JsonObject());
        modifiedArray.add(new JsonArray());
        
        List<Wheel> otherWheels = WheelJsonFactory.getWheels(modifiedArray);
        assertArrayEquals(wheels.toArray(), otherWheels.toArray());

    }

    @Test
    public void testGetSingleWheel() {
        JsonObject simpleWheel = new JsonObject();
        simpleWheel.addProperty(IVehiclePart.NAME_KEY, "Simple Wheel");
        simpleWheel.addProperty(IDistanceModifierPart.SPEED_MODIFIER_KEY, 3);
        simpleWheel.addProperty(IDistanceModifierPart.TIME_MODIFIER_KEY, 10);
        JsonObject validationObject = new JsonObject();
        validationObject.addProperty(VehicleType.BICYCLE.toString() + PartPosition.FRONT.toString(), 
                true);
        validationObject.addProperty(VehicleType.BICYCLE.toString() + 
                PartPosition.REAR.toString(), true);
        validationObject.addProperty(VehicleType.TRICYCLE.toString() + 
                PartPosition.FRONT.toString(), true);
        validationObject.addProperty(VehicleType.TRICYCLE.toString() +
                PartPosition.REAR.toString(), false);
        // At this point, the simpleWheel doesn't have a validation object
        // so it should be null.
        Wheel emptyWheel = WheelJsonFactory.getSingleWheel(simpleWheel);
        assertNull(emptyWheel);
        // Now we'll add it on and continue with the test.
        simpleWheel.add(IVehiclePart.VALID_ON_KEY, validationObject);
        
        Wheel wheel = WheelJsonFactory.getSingleWheel(simpleWheel);
        assertNotNull(wheel);
        assertTrue(wheel.getValidator().isValidForType(VehicleType.BICYCLE));
        assertTrue(wheel.getValidator().isValidForTypeWithParameters(VehicleType.TRICYCLE, 
                PartPosition.FRONT.toString()));
        assertTrue(wheel.getValidator().isValidForTypeWithParameters(VehicleType.BICYCLE, 
                PartPosition.REAR.toString()));
        assertFalse(wheel.getValidator().isValidForTypeWithParameters(VehicleType.INVALID,
                PartPosition.FRONT.toString()));
        assertFalse(wheel.getValidator().isValidForType(VehicleType.TRICYCLE));
        assertFalse(wheel.getValidator().isValidForTypeWithParameters(VehicleType.BICYCLE, 
                "Not a position"));
        assertFalse(wheel.getValidator().isValidForTypeWithParameters(VehicleType.BICYCLE, 
                PartPosition.INVALID.toString()));
        
        
    }




}
