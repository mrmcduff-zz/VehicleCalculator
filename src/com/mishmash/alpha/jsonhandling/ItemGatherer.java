package com.mishmash.alpha.jsonhandling;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;




public class ItemGatherer {

    private static final String CHAR_SET = "UTF-8";

    public static String getSmallAmountOfTextDataFromSource(String source) {
        InputStream in = null;
        String dataString = "";
        try {
            in = new URL(source).openStream();
            dataString = IOUtils.toString(in, CHAR_SET);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
        
        return dataString;
    }
    
    public static Map<String, JsonArray> seperateCategories(String rawData, List<String> categoryTitles) {
        Map<String, JsonArray> nameCategoryMap = Maps.newHashMap();
        JsonStreamParser parser = new JsonStreamParser(rawData);
        JsonElement totalStuff = null;
        JsonObject dbObject = null;
        if (parser.hasNext()) {
            totalStuff = parser.next();
            if (totalStuff.isJsonObject()) {
                dbObject = totalStuff.getAsJsonObject();
            }
        }
        
        if (dbObject != null) {
            for(String categoryName : categoryTitles) {
                if (dbObject.has(categoryName) && dbObject.get(categoryName).isJsonArray()) {
                    nameCategoryMap.put(categoryName, dbObject.getAsJsonArray(categoryName));
                }
            }
        }
        
        return nameCategoryMap;
    }
    
}
