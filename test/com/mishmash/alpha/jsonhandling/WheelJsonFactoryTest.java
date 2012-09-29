package com.mishmash.alpha.jsonhandling;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mishmash.alpha.PartPosition;
import com.mishmash.alpha.VehicleType;
import com.mishmash.alpha.vehicleparts.VehicleFrame;
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
        fail("Not yet implemented");
    }


    @Test
    public void testGetValidVehiclesForWheel() {
        JsonObject jobj = new JsonObject();
        jobj.addProperty("BicycleFront", true);
        jobj.addProperty("BicycleRear", true);
        jobj.addProperty("TricycleFront", true);
        jobj.addProperty("TricycleRear", false);
        List<VehicleType> frontWheels = WheelJsonFactory.getValidVehiclesForWheel(jobj, PartPosition.FRONT.toString());
        Assert.assertArrayEquals(Lists.newArrayList(VehicleType.BICYCLE, VehicleType.TRICYCLE).toArray(),
                frontWheels.toArray());
        assertFalse(frontWheels.contains(VehicleType.SCOOTER));
        assertFalse(frontWheels.contains(VehicleType.INVALID));
        
        List<VehicleType> rearWheels = WheelJsonFactory.getValidVehiclesForWheel(jobj, PartPosition.REAR.toString());
        VehicleType[] expected = new VehicleType[] { VehicleType.BICYCLE };
        Assert.assertArrayEquals(expected, rearWheels.toArray());
//        int answer = JOptionPane.showConfirmDialog(null, jobj.toString(), 
//                "Does this look okay?", JOptionPane.YES_NO_OPTION);
//        assertEquals(0, answer);

    }

}
