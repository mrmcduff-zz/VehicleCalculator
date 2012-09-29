package com.mishmash.alpha.jsonhandling;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
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
        fail("Not yet implemented");
    }

    @Test
    public void testGetSinglePowerPlant() {
        fail("Not yet implemented");
    }

}
