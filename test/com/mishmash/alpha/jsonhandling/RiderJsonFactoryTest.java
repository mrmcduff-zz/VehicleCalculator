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
import com.mishmash.alpha.vehicleparts.IVehiclePart;
import com.mishmash.alpha.vehicleparts.Rider;

public class RiderJsonFactoryTest {

    private static String rawData = "";
    private static JsonArray riderJsonArray = null;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        rawData = IOUtils.toString(RiderJsonFactoryTest.class.getResourceAsStream(TestUtils.LOCAL_SOURCE), "UTF-8");
        List<String> categoryList = Lists.newArrayList(Rider.PROPERTY_NAME);
        Map<String, JsonArray> categories = DataParser.seperateCategories(rawData, categoryList);
        riderJsonArray = categories.get(Rider.PROPERTY_NAME);
    }

    @Test
    public void testGetRiders() {
        assertEquals(10, riderJsonArray.size());
        List<Rider> riders = RiderJsonFactory.getRiders(riderJsonArray);
        assertEquals(10, riders.size());
        
        JsonArray modifiedArray = new JsonArray();
        for (JsonElement element : riderJsonArray) {
            modifiedArray.add(element);
        } 
        // Adding this empty object should not change the number of riders 
        // we get out of the JsonArray.
        modifiedArray.add(new JsonObject());
        
        List<Rider> sameRiders = RiderJsonFactory.getRiders(modifiedArray);
        assertEquals(10, sameRiders.size());
        
        assertArrayEquals(riders.toArray(), sameRiders.toArray());
        
        List<Rider> emptyList = RiderJsonFactory.getRiders(new JsonArray());
        assertEquals(0, emptyList.size());

    }

    @Test
    public void testGetSingleRider() {
        JsonElement element = riderJsonArray.get(0);
        Rider tomRider = RiderJsonFactory.getSingleRider(element);
        assertNotNull(tomRider);
        assertEquals("Tom", tomRider.getName());
        assertEquals(5, tomRider.getRideTimeInMinutes(), TestUtils.EQUALS_DELTA);
    }
    
    @Test
    public void testGetBadRider() {
        JsonObject noTimeObject = new JsonObject();
        noTimeObject.addProperty(IVehiclePart.NAME_KEY, "Fakey McFakerson");
        Rider nullRider = RiderJsonFactory.getSingleRider(noTimeObject);
        assertNull(nullRider);
        
        JsonObject noNameObject = new JsonObject();
        noNameObject.addProperty(Rider.RIDE_TIME_IN_MINUTES_KEY, 10000);
        nullRider = RiderJsonFactory.getSingleRider(noNameObject);
        assertNull(nullRider);
        
        JsonObject bizarreObject = new JsonObject();
        bizarreObject.addProperty("IsCompletelyCrazy", true);
        nullRider = RiderJsonFactory.getSingleRider(bizarreObject);
        assertNull(nullRider);
        
        JsonObject emptyObject = new JsonObject();
        nullRider = RiderJsonFactory.getSingleRider(emptyObject);
        assertNull(nullRider);
        
        nullRider = RiderJsonFactory.getSingleRider(null);
        assertNull(nullRider);
    }
    
    @Test
    public void testGetOverAchievingRider() {
        JsonObject overAchievingObject = new JsonObject();
        double insaneTime = 999999999;
        overAchievingObject.addProperty(IVehiclePart.NAME_KEY, "Superman");
        overAchievingObject.addProperty(Rider.RIDE_TIME_IN_MINUTES_KEY, insaneTime);
        overAchievingObject.addProperty("Showing Off", "This isn't even a real property.");
        Rider overAchiever = RiderJsonFactory.getSingleRider(overAchievingObject);
        assertNotNull(overAchiever);
        assertEquals("Superman", overAchiever.getName());
        assertEquals(insaneTime, overAchiever.getRideTimeInMinutes(), TestUtils.EQUALS_DELTA);
    }
    
    @Test
    public void testClassCastError() {
        JsonObject confusedObject = new JsonObject();
        confusedObject.addProperty(IVehiclePart.NAME_KEY, "EverythingCastsToStringSoDontBother");
        confusedObject.addProperty(Rider.RIDE_TIME_IN_MINUTES_KEY, "Clearly not a double");
        Rider confusedRider = RiderJsonFactory.getSingleRider(confusedObject);
        assertNull(confusedRider);
    }

}
