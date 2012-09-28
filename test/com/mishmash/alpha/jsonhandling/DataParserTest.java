package com.mishmash.alpha.jsonhandling;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import junit.framework.Assert;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mishmash.alpha.ItemGatherer;


import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserTest {

    private String rawData = "";
    private static final String DATA_SOURCE = "http://www.atlanticbt.com/mobiletest/json.txt";
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        
    }

    @Test
    public void testSeperateCategories() {
//        rawData = ItemGatherer.getSmallAmountOfTextDataFromSource(DATA_SOURCE);
////        JSONArray jsonArray = DataParser.seperateCategories(rawData);
//        JsonElement totalStuff  = DataParser.seperateCategories(rawData);
//
//        String ridersKey = "Riders";
//        String wheelsKey = "Wheels";
//        String framesKey = "Frames";
//        String powerPlantKey = "Powerplant";
//        
//        assert(totalStuff.isJsonObject());
//        if (totalStuff.isJsonObject()) {
//            JsonObject jobj = totalStuff.getAsJsonObject();
//            assertTrue(jobj.has("Riders"));
//            assertTrue(jobj.has("Wheels"));
//            assertTrue(jobj.has("Frames"));
//            assertTrue(jobj.has("Powerplant"));
//            
//            JsonElement riders = jobj.get(ridersKey);
//            assert(riders.isJsonArray());
//            
//            JsonElement wheels = jobj.get(wheelsKey);
//            assert(wheels.isJsonArray());
//            
//            JsonElement frames = jobj.get(framesKey);
//            assert(frames.isJsonArray());
//            
//            JsonElement powerPlants = jobj.get(powerPlantKey);
//            assert(powerPlants.isJsonArray());
//        }
        


    }

}
