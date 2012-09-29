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
import com.mishmash.alpha.vehicleparts.VehicleFrame;

public class VehicleFrameJsonFactoryTest {

    private static String rawData = "";
    private static JsonArray frameJsonArray = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        rawData = IOUtils.toString( 
                RiderJsonFactoryTest.class.getResourceAsStream(TestUtils.LOCAL_SOURCE),
                "UTF-8");
        List<String> categoryList = Lists.newArrayList(VehicleFrame.PROPERTY_NAME);
        Map<String, JsonArray> categories = DataParser.seperateCategories(rawData, categoryList);
        frameJsonArray = categories.get(VehicleFrame.PROPERTY_NAME);
    }

    @Test
    public void testGetVehicleFrames() {
        assertEquals(3, frameJsonArray.size());
        
        List<VehicleFrame> frames = FrameJsonFactory.getVehicleFrames(frameJsonArray);
        assertEquals(frameJsonArray.size(), frames.size());
        
        JsonArray modifiedArray = new JsonArray();
        for (JsonElement element : frameJsonArray) {
            modifiedArray.add(element);
        } 
        // Adding this empty object should not change the number of riders 
        // we get out of the JsonArray.
        modifiedArray.add(new JsonObject());
        
        List<VehicleFrame> sameFrames = FrameJsonFactory.getVehicleFrames(modifiedArray);
        assertArrayEquals(frames.toArray(), sameFrames.toArray());
        assertEquals(frames.size(), sameFrames.size());

    }

}
