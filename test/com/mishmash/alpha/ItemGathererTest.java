package com.mishmash.alpha;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.mishmash.alpha.jsonhandling.ItemGatherer;

public class ItemGathererTest {

    private static String rawData = "";
    private static final String LOCAL_SOURCE = "json.txt";
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        rawData = IOUtils.toString(ItemGatherer.class.getResourceAsStream(LOCAL_SOURCE), "UTF-8");
    }
    
    /**
     * Testing only the basics here. 
     */
    @Test
    public void testSeperateCategories() {
        assertTrue(rawData.length() > 0);
        String ridersKey = "Riders";
        String wheelsKey = "Wheels";
        String framesKey = "Frames";
        String powerPlantKey = "Powerplant";
        List<String> categoryList = Lists.newArrayList(ridersKey, wheelsKey, framesKey, powerPlantKey);
        Map<String, JsonArray> categories = ItemGatherer.seperateCategories(rawData, categoryList);
        assertEquals(4, categories.size());
    }

}
