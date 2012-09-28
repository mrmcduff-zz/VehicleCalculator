package com.mishmash.alpha.jsonhandling;

import static org.junit.Assert.*;

import java.util.List;

import javax.swing.JOptionPane;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mishmash.alpha.VehicleType;
import com.mishmash.alpha.vehicleparts.Wheel;

public class WheelJsonFactoryTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Test
    public void testGetWheels() {
        fail("Not yet implemented");
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
        List<VehicleType> frontWheels = WheelJsonFactory.getValidVehiclesForWheel(jobj, Wheel.FRONT);
        Assert.assertArrayEquals(Lists.newArrayList(VehicleType.BICYCLE, VehicleType.TRICYCLE).toArray(),
                frontWheels.toArray());
        assertFalse(frontWheels.contains(VehicleType.SCOOTER));
        assertFalse(frontWheels.contains(VehicleType.INVALID));
        
        List<VehicleType> rearWheels = WheelJsonFactory.getValidVehiclesForWheel(jobj, Wheel.REAR);
        VehicleType[] expected = new VehicleType[] { VehicleType.BICYCLE };
        Assert.assertArrayEquals(expected, rearWheels.toArray());
//        int answer = JOptionPane.showConfirmDialog(null, jobj.toString(), 
//                "Does this look okay?", JOptionPane.YES_NO_OPTION);
//        assertEquals(0, answer);

    }

}
