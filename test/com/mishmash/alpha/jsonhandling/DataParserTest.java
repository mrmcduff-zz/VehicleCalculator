package com.mishmash.alpha.jsonhandling;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import junit.framework.Assert;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserTest {

    private static String rawData = "";
    private static final String DATA_SOURCE = "http://www.atlanticbt.com/mobiletest/json.txt";
    private static final String LOCAL_SOURCE = "json.txt";
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        rawData = IOUtils.toString(DataParserTest.class.getResourceAsStream(LOCAL_SOURCE), "UTF-8");
    }
    


    @Test
    public void testSeperateCategories() {
//        URL location = DataParserTest.class.getClassLoader().getResource(LOCAL_SOURCE);
//        rawData = ItemGatherer.getSmallAmountOfTextDataFromSource(location.toString());
//        JSONArray jsonArray = DataParser.seperateCategories(rawData);
//        JsonElement totalStuff  = DataParser.seperateCategories(rawData);

        assertTrue(rawData.length() > 0);
        
        String ridersKey = "Riders";
        String wheelsKey = "Wheels";
        String framesKey = "Frames";
        String powerPlantKey = "Powerplant";
        List<String> categoryList = Lists.newArrayList(ridersKey, wheelsKey, framesKey, powerPlantKey);
        Map<String, JsonArray> categories = DataParser.seperateCategories(rawData, categoryList);
        assertEquals(4, categories.size());
        


    }

}
