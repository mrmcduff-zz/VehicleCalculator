package com.mishmash.alpha.jsonhandling;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mishmash.alpha.vehicleparts.Rider;

public class RiderJsonFactoryTest {

    private static String rawData = "";
    private static final String LOCAL_SOURCE = "json.txt";
    private static JsonArray riderJsonArray = null;
    private static final String RIDERS_KEY = "Riders";
    private final double EQUALS_DELTA = 0.1;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        rawData = IOUtils.toString(RiderJsonFactoryTest.class.getResourceAsStream(LOCAL_SOURCE), "UTF-8");
        List<String> categoryList = Lists.newArrayList(RIDERS_KEY);
        Map<String, JsonArray> categories = DataParser.seperateCategories(rawData, categoryList);
        riderJsonArray = categories.get(RIDERS_KEY);
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
        
        List<Rider> emptyList = RiderJsonFactory.getRiders(new JsonArray());
        assertEquals(0, emptyList.size());

    }

    @Test
    public void testGetSingleRider() {
        JsonElement element = riderJsonArray.get(0);
        Rider tomRider = RiderJsonFactory.getSingleRider(element);
        assertNotNull(tomRider);
        assertEquals("Tom", tomRider.getName());
        assertEquals(5, tomRider.getRideTimeInMinutes(), EQUALS_DELTA);
    }
    
    @Test
    public void testGetBadRider() {
        JsonObject noTimeObject = new JsonObject();
        noTimeObject.addProperty("Name", "Fakey McFakerson");
        Rider nullRider = RiderJsonFactory.getSingleRider(noTimeObject);
        assertNull(nullRider);
        
        JsonObject noNameObject = new JsonObject();
        noNameObject.addProperty("RideTimeInMinutes", 10000);
        nullRider = RiderJsonFactory.getSingleRider(noNameObject);
        assertNull(nullRider);
        
        JsonObject bizarreObject = new JsonObject();
        bizarreObject.addProperty("IsCompletelyCrazy", true);
        nullRider = RiderJsonFactory.getSingleRider(bizarreObject);
        assertNull(nullRider);
        
        JsonObject emptyObject = new JsonObject();
        nullRider = RiderJsonFactory.getSingleRider(emptyObject);
        assertNull(nullRider);
    }

}
